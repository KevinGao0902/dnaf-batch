package com.dnaf.batch.core.te.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BATCH_JOB")
public class BatchJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "JOB_ID", type = IdType.INPUT)
    @TableField(jdbcType = JdbcType.NUMERIC)
    private Long jobId;

    @TableField("JOB_NAME")
    private String jobName;

    @TableField("JOB_GROUP")
    private String jobGroup;

    @TableField("JOB_STATUS")
    private String jobStatus;

    @TableLogic
    @TableField("DELETED")
    private Integer deleted;
}
