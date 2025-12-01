package com.dnaf.batch.core.processor;



import com.dnaf.batch.core.demo.domain.LateChargeItem;
import com.dnaf.batch.core.demo.mapper.LateChargeMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LateChargeItemProcessor implements ItemProcessor<LateChargeItem, LateChargeItem> {
    @Autowired private LateChargeMapper mapper;
    @Override
    public LateChargeItem process(LateChargeItem item) throws Exception {
        mapper.runLateChargeProc(item); // 调用存储过程
        return item;
    }
}