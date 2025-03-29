package com.jaksimsam1.userservice.common.exception;


import com.jaksimsam1.userservice.common.model.enums.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
