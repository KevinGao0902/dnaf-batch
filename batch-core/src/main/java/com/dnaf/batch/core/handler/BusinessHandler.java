package com.dnaf.batch.core.handler;

import com.dnaf.batch.core.handler.demo.BatchJobContext;
import com.dnaf.batch.core.handler.demo.DataBatch;
import com.dnaf.batch.core.handler.demo.DataSlice;

import java.util.Map;

/**
 * 业务处理器接口 - 所有业务处理器必须实现此接口
 */
public interface BusinessHandler {

    /**
     * 获取业务类型
     */
    String getBusinessType();

    /**
     * 验证参数
     */
    void validateParameters(Map<String, String> params);

    /**
     * 准备数据
     */
    void prepareData(BatchJobContext context, Map<String, String> params);

    /**
     * 获取分片数据
     */
    DataSlice fetchShardData(BatchJobContext context);

    /**
     * 处理批次数据
     */
    void processBatch(DataBatch batch, BatchJobContext context);

    /**
     * 保存结果
     */
    void saveResults(BatchJobContext context);

    /**
     * 清理临时数据
     */
    void cleanup(BatchJobContext context);

    /**
     * 处理批次错误
     */
    void handleBatchError(DataBatch batch, Exception e, BatchJobContext context);

    /**
     * 获取批次大小
     */
    default int getBatchSize() {
        return 1000;
    }

    /**
     * 后处理（最后一个分片执行）
     */
    default void performPostProcessing(String batchId) {
        // 默认实现为空
    }

}
