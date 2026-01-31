package com.treasury.treasury.core.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.treasury.treasury.balance.exceptions.OfxCreationException;
import com.treasury.treasury.core.exceptionHandler.constants.ErrorConstantsEnum;
import com.treasury.treasury.user.exceptions.UserAlreadyExistsException;
import com.treasury.treasury.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleUserAlreadyExistsException(UserAlreadyExistsException exception,
      WebRequest request) {
    ApiError error = new ApiError(
        exception.getMessage(),
        ErrorConstantsEnum.USER_ALREADY_EXISTS.toString(),
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        HttpStatus.CONFLICT.value());
    exception.printStackTrace();
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException exception,
      WebRequest request) {
    ApiError error = new ApiError(
        exception.getMessage(),
        // "USER NOT FOUND",
        ErrorConstantsEnum.USER_NOT_FOUND.toString(),
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        HttpStatus.NOT_FOUND.value());
    exception.printStackTrace();
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(OfxCreationException.class)
  public ResponseEntity<ApiError> handleOfxCreationException(OfxCreationException exception,
      WebRequest request) {
    ApiError error = new ApiError(
        exception.getMessage(),
        // "CREATE BALANCES FROM OFX FILE",
        ErrorConstantsEnum.OFX_CREATION.toString(),
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        HttpStatus.UNPROCESSABLE_ENTITY.value());
    exception.printStackTrace();
    return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGenericException(
      Exception ex,
      WebRequest request) {
    ApiError error = new ApiError(
        // "INTERNAL SERVER ERROR" + ex.getMessage(),
        ErrorConstantsEnum.INTERNAL_SERVER_ERROR.toString(),
        "Internal Server Error",
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        HttpStatus.INTERNAL_SERVER_ERROR.value());

    ex.printStackTrace();
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
