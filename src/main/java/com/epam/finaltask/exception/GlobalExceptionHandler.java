package com.epam.finaltask.exception;

import com.epam.finaltask.controller.StatusCodes;
import com.epam.finaltask.dto.RemoteResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<RemoteResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex){
        RemoteResponse remoteResponse =
                RemoteResponse.create(false,ex.getErrorCode(),ex.getMessage(),null);
        return new ResponseEntity<>(remoteResponse, HttpStatus.BAD_REQUEST);
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
