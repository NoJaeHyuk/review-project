package com.reviewproject.common.exception;

import com.reviewproject.constant.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
