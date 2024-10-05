package com.jaksimsam1.userservice.user.exception;

import com.jaksimsam1.commondto.common.exception.BusinessException;
import com.jaksimsam1.commondto.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
