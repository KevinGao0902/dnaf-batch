package com.dnaf.batch.core.te.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dnaf.batch.core.te.entity.BatchJob;
import com.dnaf.batch.core.te.mapper.BatchJobMapper;
import com.dnaf.batch.core.te.service.BatchJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchJobServiceImpl extends ServiceImpl<BatchJobMapper, BatchJob> implements BatchJobService {

    private final BatchJobMapper batchJobMapper;



    @Override
    public int insert(BatchJob batchJob) {
        return batchJobMapper.insert(batchJob);
    }
}
