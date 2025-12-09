package com.dnaf.batch.core.handler.demo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 批处理任务上下文
 */
@Data
public class BatchJobContext {
    private String batchId;
    private String businessType;
    private int shardIndex;
    private int shardTotal;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AtomicLong processedCount = new AtomicLong(0);
    private AtomicLong successCount = new AtomicLong(0);
    private AtomicLong failCount = new AtomicLong(0);
    private List<String> errorMessages = new ArrayList<>();

    public void recordSuccess() {
        successCount.incrementAndGet();
        processedCount.incrementAndGet();
    }

    public void recordFailure() {
        failCount.incrementAndGet();
        processedCount.incrementAndGet();
    }

    public void recordError(String error) {
        errorMessages.add(error);
    }

    public boolean hasErrors() {
        return !errorMessages.isEmpty();
    }
}
