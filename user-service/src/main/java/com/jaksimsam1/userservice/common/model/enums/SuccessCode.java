package com.jaksimsam1.userservice.common.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.net.HttpURLConnection;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum SuccessCode implements ApiResponseCode {
    OK(HttpURLConnection.HTTP_OK, "OK", "조회")
    , NO_CONTENT(HttpURLConnection.HTTP_NO_CONTENT, "NO_CONTENT", "수정, 삭제")
    , CREATED(HttpURLConnection.HTTP_CREATED, "CREATED", "생성")
    , UNAUTHORIZED(HttpURLConnection.HTTP_UNAUTHORIZED, "UNAUTHORIZED", "인증 실패")
    ;

    private final int status;
    private final String message;
    private final String description;

    SuccessCode(int status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }
}
