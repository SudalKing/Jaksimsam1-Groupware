package com.jaksimsam1.authservice.common.exception;


import com.jaksimsam1.authservice.common.model.enums.ErrorCode;

public class AuthNotFoundException extends BusinessException {
    public AuthNotFoundException(String message, ErrorCode responseCode) {
        super(message, responseCode);
    }
}
