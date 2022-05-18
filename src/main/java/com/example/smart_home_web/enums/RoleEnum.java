package com.example.smart_home_web.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum RoleEnum {
    ADMIN(0, "管理员"),
    USER(1, "用户");

    @EnumValue
    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
