package com.example.smart_home_web.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_send_message
 */
@TableName(value ="tb_send_message")
@Data
public class SendMessage implements Serializable {
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
    private String message;

    /**
     * 
     */
    private Date sendTime;

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