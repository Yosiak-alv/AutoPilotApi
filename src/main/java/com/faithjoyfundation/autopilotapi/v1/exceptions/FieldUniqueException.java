package com.faithjoyfundation.autopilotapi.v1.exceptions;

import java.io.Serial;

public class FieldUniqueException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public FieldUniqueException(String message) {
        super(message);
    }
}
