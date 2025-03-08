package com.epam.finaltask.exception;

import com.epam.finaltask.dto.RemoteResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<RemoteResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex){
        RemoteResponse remoteResponse =
                RemoteResponse.create(false,ex.getMessage(),ex.getErrorCode(),null);
        return new ResponseEntity<>(remoteResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<RemoteResponse> handleEntityNotFound(EntityNotFoundException ex){
        RemoteResponse remoteResponse =
                RemoteResponse.create(false,ex.getMessage(),ex.getErrorCode(),null);
        return new ResponseEntity<>(remoteResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RemoteResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract validation errors
        String errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ex.getMessage());

        // Build the error response
        RemoteResponse remoteResponse = RemoteResponse.create(
                false,
                StatusCodes.INVALID_DATA.name(), // Or a custom status code
                errors,
                null
        );

        return new ResponseEntity<>(remoteResponse, HttpStatus.BAD_REQUEST);
    }
}
