package com.example.smart_home_web.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.stream.Stream;

public enum DeviceStateEnum {
    OFF(0, "已关机"),
    ON(1, "已开机"),
    CONNECT(2, "已连接");

    @EnumValue
    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    DeviceStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DeviceStateEnum forValue(Integer value) {
        DeviceStateEnum[] values = DeviceStateEnum.values();
        return Stream.of(values).filter(it -> it.getCode().equals(value)).findAny().orElse(OFF);
    }
}
