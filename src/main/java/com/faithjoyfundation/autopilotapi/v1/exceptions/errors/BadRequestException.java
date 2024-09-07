package com.faithjoyfundation.autopilotapi.v1.exceptions.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
