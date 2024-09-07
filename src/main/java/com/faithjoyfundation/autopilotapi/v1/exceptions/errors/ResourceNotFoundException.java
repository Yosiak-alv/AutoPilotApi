package com.faithjoyfundation.autopilotapi.v1.exceptions.errors;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
