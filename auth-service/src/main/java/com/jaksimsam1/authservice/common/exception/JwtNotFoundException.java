package com.jaksimsam1.authservice.common.exception;

import com.jaksimsam1.commondto.common.exception.BusinessException;
import com.jaksimsam1.commondto.model.enums.ErrorCode;

public class JwtNotFoundException extends BusinessException {

    public JwtNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
