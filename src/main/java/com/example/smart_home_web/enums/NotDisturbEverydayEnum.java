package com.example.smart_home_web.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.stream.Stream;

public enum NotDisturbEverydayEnum {
    OFF(0, "否"),
    ON(1, "是");

    @EnumValue
    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    NotDisturbEverydayEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static NotDisturbEverydayEnum forValue(String value) {
        NotDisturbEverydayEnum[] values = NotDisturbEverydayEnum.values();
        return Stream.of(values).filter(it -> it.getMessage().equals(value)).findAny().orElse(OFF);
    }
}
