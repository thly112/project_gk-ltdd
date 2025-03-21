package com.example.project_gk;

public class ApiResponse<T> {
    private Boolean status;
    private String message;
    private T body;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getBody() {
        return body;
    }
}
