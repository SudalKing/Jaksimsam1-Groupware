package com.jaksimsam1.userservice.domain.user.exception;

import com.jaksimsam1.commondto.common.exception.BusinessException;
import com.jaksimsam1.commondto.model.enums.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
