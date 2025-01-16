package com.treasury.treasury.user.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String document) {
    super("USER WITH DOCUMENT [" + document + "] ALREADY EXISTS");
  }
}
