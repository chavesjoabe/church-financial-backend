package com.treasury.treasury.user.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String document) {
    super("USER WITH DOCUMENT [" + document + "] NOT FOUND");
  }
}
