package com.dnaf.batch.core.te.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dnaf.batch.core.te.entity.BatchJob;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchJobMapper extends BaseMapper<BatchJob> {


    int insert(BatchJob batchJob);
}
