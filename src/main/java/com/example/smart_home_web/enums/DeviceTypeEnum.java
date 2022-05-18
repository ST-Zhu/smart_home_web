package com.example.smart_home_web.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.stream.Stream;

public enum DeviceTypeEnum {
    OTHER(0, "其他"),
    AUDIO(1, "智能音响"),
    LIGHT(2, "智能灯"),
    CURTAIN(3, "智能窗帘"),
    DOOR(4, "智能门"),
    TV(5, "智能电视"),
    AIR(6, "智能空调"),
    HEATER(7, "智能热水器"),
    KITCHEN(8, "智能厨房"),
    ROBOT(9, "智能机器人");

    @EnumValue
    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    DeviceTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DeviceTypeEnum forValue(String value) {
        DeviceTypeEnum[] values = DeviceTypeEnum.values();
        return Stream.of(values).filter(it -> it.getMessage().equals(value)).findAny().orElse(OTHER);
    }
}
