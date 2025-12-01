package com.dnaf.batch.core.demo.batch;

import com.dnaf.batch.core.demo.domain.LateChargeItem;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class LateChargeItemWriter implements ItemWriter<LateChargeItem> {
    private final StringRedisTemplate redisTemplate;

    public LateChargeItemWriter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(Chunk<? extends LateChargeItem> chunk) throws Exception {
        for (LateChargeItem item : chunk) {
            redisTemplate.opsForValue().set("batch:latecharge:" + item.getId(), "done");
            // 可在此实际入库或归档结果
        }
    }
}