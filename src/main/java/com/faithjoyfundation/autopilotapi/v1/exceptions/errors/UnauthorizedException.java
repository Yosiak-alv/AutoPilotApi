package com.faithjoyfundation.autopilotapi.v1.exceptions.errors;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
