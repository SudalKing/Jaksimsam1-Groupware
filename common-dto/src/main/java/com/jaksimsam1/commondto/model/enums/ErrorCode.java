package com.jaksimsam1.commondto.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.net.HttpURLConnection;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode implements ApiResponseCode{

    /* 공통 에러 */
    INVALID_INPUT(HttpURLConnection.HTTP_BAD_REQUEST, "INVALID_INPUT", "Invalid Input Value")
    , BAD_METHOD(HttpURLConnection.HTTP_BAD_METHOD, "METHOD_NOT_ALLOWED", "Method Not Allowed")
    , ENTITY_NOT_FOUND(HttpURLConnection.HTTP_BAD_REQUEST, "ENTITY_NOT_FOUND", "Entity Not Found")
    , UNAUTHORIZED(HttpURLConnection.HTTP_UNAUTHORIZED, "UNAUTHORIZED", "Unauthorized")
    , ACCESS_DENIED(HttpURLConnection.HTTP_FORBIDDEN, "ACCESS_DENIED", "Access is Denied")
    , METHOD_NOT_FOUND(HttpURLConnection.HTTP_NOT_FOUND, "METHOD_NOT_FOUND", "Not Found")
    , SERVER_ERROR(HttpURLConnection.HTTP_INTERNAL_ERROR, "SERVER_ERROR", "Internal Server Error"),
    ;

    private final int status;
    private final String message;
    private final String description;

    ErrorCode(int status, String message, String description) {
        this.status = status;
        this.description = description;
        this.message = message;
    }
}
