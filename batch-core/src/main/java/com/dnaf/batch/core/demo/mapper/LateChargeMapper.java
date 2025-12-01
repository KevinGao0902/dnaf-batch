package com.dnaf.batch.core.demo.mapper;

import com.dnaf.batch.core.demo.domain.LateChargeItem;
import com.dnaf.batch.core.demo.domain.PartitionKey;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LateChargeMapper {
    List<PartitionKey> queryPartitionKeys(@Param("gridSize") int gridSize);

    List<LateChargeItem> selectLateChargeByRange(@Param("startId") int startId, @Param("endId") int endId);

    void runLateChargeProc(LateChargeItem item);
}