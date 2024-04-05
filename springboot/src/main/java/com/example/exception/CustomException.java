package com.example.exception;

import com.example.common.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private String code;
    private String msg;

    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
