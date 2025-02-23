package com.jaksimsam1.commondto.common.exception;

import com.jaksimsam1.commondto.model.enums.ApiResponseCode;
import com.jaksimsam1.commondto.model.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ApiResponseCode errorCode;

    public BusinessException(String message, ApiResponseCode responseCode) {
        super(message);
        this.errorCode = responseCode;
    }

    public BusinessException(ApiResponseCode responseCode) {
        super(responseCode.getMessage());
    }
}
