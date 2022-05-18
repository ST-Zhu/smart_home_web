package com.example.smart_home_web.enums;

public enum PromptEnum {
    PROMPT_NONE(0, "暂无信息"),
    PROMPT_SOME(1, "信息如下"),
    PROMPT_SUCCESS(2, "操作成功"),
    PROMPT_FAIL(3, "操作失败");


    private Integer code;
    private String message;

    PromptEnum(Integer roleNumber, String roleName) {
        this.code = roleNumber;
        this.message = roleName;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
