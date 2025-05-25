package com.jaksimsam1.authservice.common.model.enums;

public interface ApiResponseCode {
    int getStatus();
    String getMessage();
    String getDescription();
}
