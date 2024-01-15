package org.example.exception;

public class Unauthorized extends BaseRuntimeException {
    public Unauthorized(final String message) {
        super(message);
    }
}
