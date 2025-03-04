package com.epam.finaltask.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String statusCode, String message) {
        super(String.format("[%s] %s", statusCode, message));
    }
}
