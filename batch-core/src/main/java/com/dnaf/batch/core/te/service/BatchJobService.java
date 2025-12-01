package com.dnaf.batch.core.te.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dnaf.batch.core.te.entity.BatchJob;

import java.util.List;

public interface BatchJobService extends IService<BatchJob> {

    int insert(BatchJob batchJob);
}
