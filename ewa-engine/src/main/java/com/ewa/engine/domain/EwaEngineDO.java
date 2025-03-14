package com.ewa.engine.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
@Data
@TableName("walle_engine")
public class EwaEngineDO {

    @TableId(value = "id")
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "content")
    private String content;

    @TableField(value = "status")
    private String status;

    @TableField(value = "version")
    private String version;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

}
