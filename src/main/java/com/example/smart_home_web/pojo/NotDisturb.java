package com.example.smart_home_web.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.example.smart_home_web.enums.NotDisturbEverydayEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName tb_not_disturb
 */
@TableName(value ="tb_not_disturb")
@Data
public class NotDisturb implements Serializable {
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
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd'T'HH:mm")
    private Date startTime;

    /**
     * 
     */
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd'T'HH:mm")
    private Date endTime;

    /**
     * 
     */
    private NotDisturbEverydayEnum isEveryday;

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