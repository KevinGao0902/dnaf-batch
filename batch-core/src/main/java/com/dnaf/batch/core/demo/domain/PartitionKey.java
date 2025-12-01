package com.dnaf.batch.core.demo.domain;

import lombok.Data;

@Data
public class PartitionKey {
    private Integer startId;
    private Integer endId;
}