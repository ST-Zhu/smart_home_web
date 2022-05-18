package com.example.smart_home_web.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.stream.Stream;

public enum GenderEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    @EnumValue
    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }


    GenderEnum(Integer genderNumber, String genderName) {
        this.code = genderNumber;
        this.message = genderName;
    }

    public static GenderEnum forValue(String value) {
        GenderEnum[] values = GenderEnum.values();
        return Stream.of(values).filter(it -> it.getMessage().equals(value)).findAny().orElse(MALE);
    }
}
