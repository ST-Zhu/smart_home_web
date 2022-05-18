package com.example.smart_home_web.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName tb_connect
 */
@TableName(value ="tb_connect")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connect implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    private Date connectTime;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer deviceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}