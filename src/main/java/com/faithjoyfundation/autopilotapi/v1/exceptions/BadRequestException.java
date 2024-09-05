package com.faithjoyfundation.autopilotapi.v1.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
