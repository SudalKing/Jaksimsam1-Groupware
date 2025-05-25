package com.jaksimsam1.authservice.common.exception;


import com.jaksimsam1.authservice.common.model.enums.ErrorCode;

public class JwtNotFoundException extends BusinessException {

    public JwtNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
