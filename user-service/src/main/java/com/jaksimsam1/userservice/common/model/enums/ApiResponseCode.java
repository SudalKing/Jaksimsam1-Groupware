package com.jaksimsam1.userservice.common.model.enums;

public interface ApiResponseCode {
    int getStatus();
    String getMessage();
    String getDescription();
}
