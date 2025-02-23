package com.jaksimsam1.commondto.common.exception;

import com.jaksimsam1.commondto.model.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Valid 에러 캐치
     * 주로 @RequestBody, @RequestPart 에서 발생
     * @param e MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotInvalidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT, e.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않는 HTTP method 호출 시 캐치
     * @param e HttpRequestMethodNotSupportedException
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_METHOD);
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * BusinessException 발생 시 캐치
     * @param e BusinessException
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException", e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getDescription()));
    }

    /**
     * 허가되지 않은 접근 시 캐치
     * @param e AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
