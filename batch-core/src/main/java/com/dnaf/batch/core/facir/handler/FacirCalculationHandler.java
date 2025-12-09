package com.dnaf.batch.core.facir.handler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dnaf.batch.core.facir.entity.FACIR;
import com.dnaf.batch.core.facir.service.FACIRService;
import com.dnaf.batch.core.handler.AbstractBusinessHandler;
import com.dnaf.batch.core.handler.demo.BatchJobContext;
import com.dnaf.batch.core.handler.demo.DataBatch;
import com.dnaf.batch.core.handler.demo.DataSlice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FacirCalculationHandler extends AbstractBusinessHandler {

    @Autowired
    private FACIRService facirService;

    @Override
    public String getBusinessType() {
        return "facir_calculation";
    }

    @Override
    public void validateParameters(Map<String, String> params) {
        super.validateParameters(params);

        // 验证必要参数
        if (!params.containsKey("calculationDate")) {
            throw new IllegalArgumentException("缺少计算日期参数(calculationDate)");
        }

        try {
            LocalDate.parse(params.get("calculationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new IllegalArgumentException("计算日期格式不正确，应为 yyyy-MM-dd");
        }
    }

    @Override
    public void prepareData(BatchJobContext context, Map<String, String> params) {
        super.prepareData(context, params);
        // 可以在这里做一些预处理工作，例如初始化统计数据等
    }

    @Override
    public DataSlice fetchShardData(BatchJobContext context) {
        // 查询需要处理的 FACIR 数据
        QueryWrapper<FACIR> queryWrapper = new QueryWrapper<>();
        // 示例条件：查询尚未计算过或者超过某个时间的记录
        queryWrapper.isNull("firmtintcalcule").or().lt("firdtfin", context.getStartTime());
        List<FACIR> allRecords = facirService.list(queryWrapper);

        log.info("查询到{}条待处理记录", allRecords.size());

        // 根据分片信息筛选属于当前分片的数据
        List<Long> dataIds = allRecords.stream()
                .map(FACIR::getFacid)
                .filter(id -> id.hashCode() % context.getShardTotal() == context.getShardIndex())
                .collect(Collectors.toList());

        DataSlice dataSlice = new DataSlice();
        dataSlice.setDataIds(dataIds);
        return dataSlice;
    }

    @Override
    public void processBatch(DataBatch batch, BatchJobContext context) {
        if (batch.isEmpty()) {
            return;
        }

        // 批量获取数据
        List<FACIR> facirList = facirService.listByIds(batch.getDataIds());

        // 对每条记录执行罚息计算逻辑
        for (FACIR facir : facirList) {
            try {
                // 这里添加实际的罚息计算逻辑
                calculateLatePaymentInterest(facir);

                // 更新记录
                facirService.updateById(facir);

                context.recordSuccess();
            } catch (Exception e) {
                context.recordFailure();
                context.recordError("处理FACIR ID=" + facir.getFacid() + "失败：" + e.getMessage());
                log.error("处理FACIR记录失败，ID={}", facir.getFacid(), e);
            }
        }
    }

    @Override
    public void saveResults(BatchJobContext context) {
        // 在processBatch中已经更新了数据，此处可做汇总统计等操作
        log.info("完成FACIR罚息计算任务，成功处理{}条记录", context.getSuccessCount().get());
    }

    @Override
    public void cleanup(BatchJobContext context) {
        super.cleanup(context);
        // 清理资源
    }

    @Override
    protected String getSourceTableName() {
        return "FACIR";
    }

    @Override
    protected String getIdColumnName() {
        return "facid";
    }

    /**
     * 实际的罚息计算逻辑
     * @param facir 待计算的记录
     */
    private void calculateLatePaymentInterest(FACIR facir) {
        // 示例计算逻辑（需替换为真实业务逻辑）
        long baseAmount = facir.getFirmtforfait() != null ? facir.getFirmtforfait() : 0L;
        double interestRate = 0.05; // 假设年利率5%

        // 简单示例计算
        long calculatedInterest = (long)(baseAmount * interestRate / 365 *
                java.time.temporal.ChronoUnit.DAYS.between(
                        facir.getFirdtdepart().toInstant(),
                        facir.getFirdtfin().toInstant()));

        facir.setFirmtintcalcule(calculatedInterest);
        facir.setFirmtintfacturable(calculatedInterest);
    }
}
