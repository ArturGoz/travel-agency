package com.epam.finaltask.exception;

public class EntityAlreadyExistsException extends RuntimeException {
  public EntityAlreadyExistsException(String message) {
    super(message);
  }

  public EntityAlreadyExistsException(String statusCode, String message) {
    super(String.format("[%s] %s", statusCode, message));
  }

  public String getErrorCode() {
    return "DUPLICATE_USERNAME";
  }
}
