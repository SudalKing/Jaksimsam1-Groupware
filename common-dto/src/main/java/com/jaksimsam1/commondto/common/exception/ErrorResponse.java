package com.jaksimsam1.commondto.common.exception;

import com.jaksimsam1.commondto.model.enums.ErrorCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private int status;
    private String message;
    private String code;
    private List<FieldError> fieldErrors;

    private ErrorResponse(ErrorCode errorCode, List<FieldError> fieldErrors) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.code = errorCode.getDescription();
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getDescription();
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.fieldErrors = new ArrayList<>();
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, bindingResult.getFieldErrors());
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

}
