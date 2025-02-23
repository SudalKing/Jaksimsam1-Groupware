package com.jaksimsam1.commondto.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jaksimsam1.commondto.model.enums.ApiResponseCode;
import com.jaksimsam1.commondto.model.enums.ErrorCode;
import com.jaksimsam1.commondto.model.enums.SuccessCode;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Create 성공 메소드(201)
    public static <T> ApiResponse<T> create() {
        return build(SuccessCode.CREATED, null);
    }

    // Read 성곰 메소드(200)
    public static <T> ApiResponse<T> read(T data) {
        return build(SuccessCode.OK, data);
    }

    // Update 성공 메소드(204)
    public static <T> ApiResponse<T> update() {
        return build(SuccessCode.NO_CONTENT, null);
    }

    // Delete 성공 메소드(204)
    public static <T> ApiResponse<T> delete() {
        return build(SuccessCode.NO_CONTENT, null);
    }

    // 요청 실패 메소드
    public static <T> ApiResponse<T> error() {
        return build(ErrorCode.SERVER_ERROR, null);
    }

    private static <T> ApiResponse<T> build(ApiResponseCode responseCode, T data) {
        return new ApiResponse<>(responseCode.getStatus(), responseCode.getMessage(), data);
    }

}
