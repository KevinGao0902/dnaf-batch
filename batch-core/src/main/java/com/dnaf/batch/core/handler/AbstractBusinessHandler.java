package com.dnaf.batch.core.handler;


import com.dnaf.batch.core.handler.demo.BatchJobContext;
import com.dnaf.batch.core.handler.demo.DataBatch;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 抽象业务处理器 - 提供通用实现，子类只需实现特定方法
 */
@Slf4j
public abstract class AbstractBusinessHandler implements BusinessHandler {

    /**
     * 默认的参数验证
     */
    @Override
    public void validateParameters(Map<String, String> params) {
        // 子类可以覆盖此方法进行参数验证
        log.debug("参数验证通过: {}", params);
    }

    /**
     * 默认的数据准备
     */
    @Override
    public void prepareData(BatchJobContext context, Map<String, String> params) {
        // 子类可以覆盖此方法进行数据准备
        log.debug("数据准备完成");
    }

    /**
     * 默认的清理方法
     */
    @Override
    public void cleanup(BatchJobContext context) {
        // 子类可以覆盖此方法进行清理
        log.debug("清理完成");
    }

    /**
     * 默认的错误处理方法
     */
    @Override
    public void handleBatchError(DataBatch batch, Exception e, BatchJobContext context) {
        // 记录错误日志，子类可以覆盖此方法进行特定错误处理
        log.error("批次处理失败，批次大小: {}", batch.size(), e);
        context.recordError(e.getMessage());
    }

    /**
     * 获取业务数据表名（供子类实现）
     */
    protected abstract String getSourceTableName();

    /**
     * 获取业务ID字段名（供子类实现）
     */
    protected abstract String getIdColumnName();
}
