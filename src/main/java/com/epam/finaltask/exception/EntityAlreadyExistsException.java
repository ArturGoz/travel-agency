package com.epam.finaltask.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return "DUPLICATE_USERNAME";
    }
}
