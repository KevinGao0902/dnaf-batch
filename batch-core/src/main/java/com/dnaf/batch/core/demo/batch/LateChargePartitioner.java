package com.dnaf.batch.core.demo.batch;


import com.dnaf.batch.core.demo.domain.PartitionKey;
import com.dnaf.batch.core.demo.mapper.LateChargeMapper;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LateChargePartitioner implements Partitioner {
    private final LateChargeMapper lateChargeMapper;

    public LateChargePartitioner(LateChargeMapper lateChargeMapper) {
        this.lateChargeMapper = lateChargeMapper;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        List<PartitionKey> keys = lateChargeMapper.queryPartitionKeys(gridSize);
        Map<String, ExecutionContext> map = new HashMap<>();
        int i = 0;
        for (PartitionKey key : keys) {
            ExecutionContext ctx = new ExecutionContext();
            ctx.putInt("startId", key.getStartId());
            ctx.putInt("endId", key.getEndId());
            map.put("partition" + i, ctx);
            i++;
        }
        return map;
    }
}