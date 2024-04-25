package com.example.common.enums;

public enum StatusEnum {
    TO_BE_REVIEWED("待审核"),
    APPROVE("通过"),
    REJECT("拒绝");

    public String value;

    StatusEnum(String value) {
        this.value = value;
    }
}