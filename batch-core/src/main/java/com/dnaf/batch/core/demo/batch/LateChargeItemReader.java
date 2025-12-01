package com.dnaf.batch.core.demo.batch;


import com.dnaf.batch.core.demo.domain.LateChargeItem;
import com.dnaf.batch.core.demo.mapper.LateChargeMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LateChargeItemReader implements ItemReader<LateChargeItem> {

    private List<LateChargeItem> items = new ArrayList<>();
    private Iterator<LateChargeItem> iterator;

    private final LateChargeMapper lateChargeMapper;

    // 由Spring Batch Partition分配给每个分区的ExecutionContext参数在Step里拿
    public LateChargeItemReader(LateChargeMapper lateChargeMapper) {
        this.lateChargeMapper = lateChargeMapper;
    }

    // 在实际Step配置时通过JobParameters传入startId、endId
    public void init(int startId, int endId) {
        items = lateChargeMapper.selectLateChargeByRange(startId, endId);
        iterator = items.iterator();
    }

    @Override
    public LateChargeItem read() {
        return (iterator != null && iterator.hasNext()) ? iterator.next() : null;
    }
}