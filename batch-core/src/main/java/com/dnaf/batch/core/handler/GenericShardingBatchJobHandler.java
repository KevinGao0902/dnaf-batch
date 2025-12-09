package com.dnaf.batch.core.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dnaf.batch.core.handler.demo.BatchJobContext;
import com.dnaf.batch.core.handler.demo.DataBatch;
import com.dnaf.batch.core.handler.demo.DataSlice;
import com.dnaf.batch.core.handler.demo.JobParameters;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class GenericShardingBatchJobHandler {

    @Autowired
    private BusinessHandlerFactory businessHandlerFactory;

    //@Autowired
    //private BatchJobMonitor batchJobMonitor;

    /**
     * 通用的分片批处理任务入口
     * 通过jobParam指定业务类型，例如：penalty_calculation, accounting_entry, repayment_detail
     */
    @XxlJob("genericShardingBatchJobHandler")
    public void execute() {

        // 直接从 XxlJobHelper 获取参数
        String jobParam = XxlJobHelper.getJobParam();

        if (StringUtils.isBlank(jobParam)) {
            log.error("任务参数为空");
            XxlJobHelper.handleFail("任务参数不能为空");
            return;
        }
        // 1. 解析任务参数
        log.info("开始执行任务，任务参数: {}", jobParam);
        JobParameters parameters = parseJobParameters(jobParam);
        String businessType = parameters.getBusinessType();
        String batchId = generateBatchId(businessType);

        // 2. 获取分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        log.info("开始执行{}批处理任务，批次ID: {}, 分片: [{}/{}]",
                businessType, batchId, shardIndex, shardTotal);

        // 3. 创建任务上下文
        BatchJobContext context = createJobContext(batchId, businessType, shardIndex, shardTotal);

        try {
            // 4. 性能监控开始
            //batchJobMonitor.start(batchId, businessType, shardIndex, shardTotal);
            log.info("性能监控开始，批次ID: {}, 业务类型: {}, 分片: [{}/{}]",
                    batchId, businessType, shardIndex, shardTotal);

            // 5. 根据业务类型获取对应的处理器
            BusinessHandler handler = businessHandlerFactory.getHandler(businessType);
            if (handler == null) {
                throw new IllegalArgumentException("未找到业务类型对应的处理器: " + businessType);
            }

            // 6. 验证参数
            handler.validateParameters(parameters.getCustomParams());

            // 7. 执行业务处理
            executeBusinessWithTemplate(context, handler, parameters);

            // 8. 如果是最后一个分片，执行后处理
            if (isLastShard(shardIndex, shardTotal)) {
                performPostProcessing(batchId, businessType, handler);
            }

            XxlJobHelper.handleSuccess(String.format("%s批处理任务执行成功", businessType));

        } catch (Exception e) {
            log.error("批处理任务执行失败: {}", businessType, e);
            XxlJobHelper.handleFail(String.format("%s批处理任务执行失败: %s",
                    businessType, e.getMessage()));
            log.error("批处理任务执行失败: {}", businessType, e);
            //batchJobMonitor.recordFailure(batchId, businessType, shardIndex, e);

        } finally {
            log.info("性能监控结束，批次ID: {}, 业务类型: {}, 分片: [{}/{}]",
                    batchId, businessType, shardIndex, shardTotal);
            // 9. 性能监控结束
            //batchJobMonitor.end(batchId, businessType, shardIndex);

        }
    }

    /**
     * 使用模板方法执行业务逻辑
     */
    private void executeBusinessWithTemplate(BatchJobContext context,
                                             BusinessHandler handler,
                                             JobParameters parameters) {
        String businessType = context.getBusinessType();
        int shardIndex = context.getShardIndex();

        XxlJobHelper.log("开始处理{}业务，分片: {}", businessType, shardIndex);

        // 阶段1: 数据准备
        XxlJobHelper.log("分片{}: 准备{}数据", shardIndex, businessType);
        handler.prepareData(context, parameters.getParams());

        // 阶段2: 分片数据获取
        XxlJobHelper.log("分片{}: 获取{}分片数据", shardIndex, businessType);
        DataSlice dataSlice = handler.fetchShardData(context);

        if (dataSlice.isEmpty()) {
            XxlJobHelper.log("分片{}: 没有需要处理的{}数据", shardIndex, businessType);
            return;
        }

        // 阶段3: 数据处理
        XxlJobHelper.log("分片{}: 处理{}数据，共{}条",
                shardIndex, businessType, dataSlice.getTotalCount());

        AtomicLong successCount = new AtomicLong(0);
        AtomicLong failCount = new AtomicLong(0);

        // 分批处理
        int batchSize = handler.getBatchSize();
        for (int i = 0; i < dataSlice.getTotalCount(); i += batchSize) {
            int end = Math.min(i + batchSize, dataSlice.getTotalCount());
            DataBatch batch = dataSlice.getBatch(i, end);

            try {
                handler.processBatch(batch, context);
                successCount.addAndGet(batch.size());

                // 进度日志
                if (i % 1000 == 0) {
                    XxlJobHelper.log("分片{}: 已处理{}/{}条{}数据",
                            shardIndex, i + batch.size(), dataSlice.getTotalCount(), businessType);
                }

            } catch (Exception e) {
                failCount.addAndGet(batch.size());
                log.error("批次处理失败，批次范围: {}-{}", i, end, e);
                handler.handleBatchError(batch, e, context);
            }
        }

        // 阶段4: 结果存储
        XxlJobHelper.log("分片{}: 存储{}处理结果", shardIndex, businessType);
        handler.saveResults(context);

        // 阶段5: 清理临时数据
        XxlJobHelper.log("分片{}: 清理{}临时数据", shardIndex, businessType);
        handler.cleanup(context);

        XxlJobHelper.log("分片{}: {}处理完成，成功: {}，失败: {}",
                shardIndex, businessType, successCount.get(), failCount.get());
    }

    /**
     * 解析任务参数
     */
    private JobParameters parseJobParameters(String jobParam) {
        JobParameters parameters = new JobParameters();

        log.info("任务参数: {}", jobParam);

        if (jobParam == null || jobParam.isEmpty()) {
            throw new IllegalArgumentException("任务参数不能为空");
        }

        // 解析参数格式：businessType=penalty_calculation&startDate=2024-01-01&endDate=2024-01-31
        String[] pairs = jobParam.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                parameters.setParam(keyValue[0], keyValue[1]);
            }
        }

        if (!parameters.hasParam("businessType")) {
            throw new IllegalArgumentException("任务参数必须包含businessType");
        }

        return parameters;
    }

    /**
     * 生成批次ID
     */
    private String generateBatchId(String businessType) {
        String timestamp = LocalDateTime.now().toString().replace(":", "").replace(".", "");
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s_%s_%s", businessType.toUpperCase(), timestamp, uuid);
    }

    /**
     * 创建任务上下文
     */
    private BatchJobContext createJobContext(String batchId, String businessType,
                                             int shardIndex, int shardTotal) {
        BatchJobContext context = new BatchJobContext();
        context.setBatchId(batchId);
        context.setBusinessType(businessType);
        context.setShardIndex(shardIndex);
        context.setShardTotal(shardTotal);
        context.setStartTime(LocalDateTime.now());
        return context;
    }

    /**
     * 检查是否为最后一个分片
     */
    private boolean isLastShard(int shardIndex, int shardTotal) {
        return shardIndex == shardTotal - 1;
    }

    /**
     * 执行后处理
     */
    private void performPostProcessing(String batchId, String businessType, BusinessHandler handler) {
        XxlJobHelper.log("开始执行{}后处理", businessType);

        try {
            handler.performPostProcessing(batchId);
            XxlJobHelper.log("{}后处理完成", businessType);

        } catch (Exception e) {
            log.error("后处理失败: {}", businessType, e);
            XxlJobHelper.log("{}后处理失败: {}", businessType, e.getMessage());
        }
    }

}
