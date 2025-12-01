package com.dnaf.batch.core.demo.domain;

import lombok.Data;

@Data
public class LateChargeItem {
    private Integer id;
    private Integer facId;
    private String dueDate;
    // 其它业务字段
}