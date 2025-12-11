package com.pm.patient_service.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private String status;
    private int statusCode;
        private String message;
        private LocalDateTime timestamp;
        private T data;

    public ApiResponse(String status, int statusCode, String message, LocalDateTime timestamp, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
