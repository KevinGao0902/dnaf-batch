package com.dnaf.batch.core.handler.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据切片 - 表示一个分片的所有数据
 */
@Data
public class DataSlice {
    private List<Long> dataIds = new ArrayList<>();
    private int totalCount;
    private int shardIndex;
    private int shardTotal;

    public boolean isEmpty() {
        return dataIds.isEmpty();
    }

    public int getTotalCount() {
        return dataIds.size();
    }

    public DataBatch getBatch(int start, int end) {
        if (start < 0 || end > dataIds.size() || start >= end) {
            throw new IllegalArgumentException("无效的批次范围");
        }

        DataBatch batch = new DataBatch();
        batch.setDataIds(new ArrayList<>(dataIds.subList(start, end)));
        return batch;
    }
}

