package com.example.smart_home_web.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.stream.Stream;

public enum IsSharedEnum {
    OFF(0, "已关闭共享"),
    ON(1, "已开启共享");

    @EnumValue
    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    IsSharedEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static IsSharedEnum forValue(Integer value) {
        IsSharedEnum[] values = IsSharedEnum.values();
        return Stream.of(values).filter(it -> it.getCode().equals(value)).findAny().orElse(OFF);
    }
}
