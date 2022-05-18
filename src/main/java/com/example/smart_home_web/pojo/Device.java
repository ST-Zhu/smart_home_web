package com.example.smart_home_web.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.example.smart_home_web.enums.DeviceStateEnum;
import com.example.smart_home_web.enums.DeviceTypeEnum;
import com.example.smart_home_web.enums.IsSharedEnum;
import lombok.Data;

/**
 * 
 * @TableName tb_device
 */
@TableName(value ="tb_device")
@Data
public class Device implements Serializable {
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
    private String name;

    /**
     * 
     */
    private DeviceTypeEnum type;

    /**
     * 
     */
    private String introduction;

    /**
     * 
     */
    private String review;

    /**
     * 68:39:43:CA:2F:71
     */
    private String gateway;

    /**
     *
     */
    private IsSharedEnum isShared;

    /**
     * 
     */
    private DeviceStateEnum state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}