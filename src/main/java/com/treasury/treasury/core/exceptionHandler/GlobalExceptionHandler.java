package com.treasury.treasury.core.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.treasury.treasury.user.exceptions.UserAlreadyExistsException;
import com.treasury.treasury.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleUserAlreadyExistsException(UserAlreadyExistsException exception,
      WebRequest request) {
    ApiError error = new ApiError(
        exception.getMessage(),
        "USER ALREADY EXISTS",
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        HttpStatus.CONFLICT.value());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException exception,
      WebRequest request) {
    ApiError error = new ApiError(
        exception.getMessage(),
        "USER NOT FOUND",
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGenericException(
      Exception ex,
      WebRequest request) {
    ApiError error = new ApiError(
        "DEU MUITO RUIM",
        "Internal Server Error",
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
