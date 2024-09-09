package com.faithjoyfundation.autopilotapi.v1.common.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    // Only include data if it's not null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
