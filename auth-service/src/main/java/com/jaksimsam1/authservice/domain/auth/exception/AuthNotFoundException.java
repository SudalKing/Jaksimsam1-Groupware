package com.jaksimsam1.authservice.domain.auth.exception;

import com.jaksimsam1.commondto.common.exception.BusinessException;
import com.jaksimsam1.commondto.model.enums.ErrorCode;

public class AuthNotFoundException extends BusinessException {
    public AuthNotFoundException(String message, ErrorCode responseCode) {
        super(message, responseCode);
    }
}
