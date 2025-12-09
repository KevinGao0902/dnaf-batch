package com.dnaf.batch.core.handler.demo;


// DataBatch.java

import lombok.Data;

import java.util.List;

/**
 * 数据批次 - 表示一批要处理的数据
 */
@Data
public class DataBatch {
    private List<Long> dataIds;

    public int size() {
        return dataIds != null ? dataIds.size() : 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
