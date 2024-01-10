package org.example.exception;

public class UserNotFound extends BaseRuntimeException {

    public UserNotFound(final String message) {
        super(message);
    }

}
