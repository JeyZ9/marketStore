package com.app.marketstore.config;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage(){
        return message;
    }

    public String getTimestamp(){
        return LocalDateTime.now().toString();
    }
}
