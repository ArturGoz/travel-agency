package com.epam.finaltask.exception;

public class EntityAlreadyExistsException extends MyException {
  public EntityAlreadyExistsException(String message) {
    super(message);
  }
  public EntityAlreadyExistsException(String message, String statusCode) {
    super(message,statusCode);
  }
}
