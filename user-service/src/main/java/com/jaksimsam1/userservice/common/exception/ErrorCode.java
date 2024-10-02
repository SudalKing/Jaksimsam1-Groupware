package com.jaksimsam1.userservice.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    /* 공통 에러 */
    INVALID_INPUT_VALUE("Invalid Input Value", "C001", 400),
    METHOD_NOT_ALLOWED("Method Not Allowed", "C002", 405),
    ENTITY_NOT_FOUND("Entity Not Found", "C003", 400),
    INVALID_TYPE_VALUE("Invalid Type Value", "C004", 400),
    HANDLE_ACCESS_DENIED("Access is Denied", "C005", 403),
    INTERNAL_SERVER_ERROR("Internal Server Error", "C006", 500),
    ACCESS_DENIED("Access Denied", "C007", 404),

    ;

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
