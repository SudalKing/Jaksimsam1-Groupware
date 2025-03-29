package com.jaksimsam1.authservice.common.exception;


import com.jaksimsam1.authservice.common.model.enums.ErrorCode;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException(String message, ErrorCode responseCode) {
        super(message, responseCode);
    }
}
