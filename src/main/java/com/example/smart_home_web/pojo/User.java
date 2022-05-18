package com.example.smart_home_web.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.example.smart_home_web.enums.GenderEnum;
import com.example.smart_home_web.enums.RoleEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @TableName tb_user
 */
@TableName(value ="tb_user")
@Data
public class User implements Serializable {
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
    @NotNull(message = "用户名不能为空")
    @Length(min = 1,max = 15,message = "请输入长度为1到15位的用户名")
    private String username;

    /**
     *
     */
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "密码由数字和大小写字母组成")
    private String password;

    /**
     *
     */
    private RoleEnum role;

    /**
     *
     */
    @NotNull(message = "邮箱不能为空")
    @Email(message = "请输入正确格式的邮箱")
    private String email;

    /**
     *
     */
    private String nickname;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private GenderEnum gender;

    /**
     * 
     */
    private Integer age;

    /**
     * 
     */
    private String phoneNumber;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}