package com.treasury.treasury.balance.exceptions;

public class OfxCreationException extends RuntimeException {
  public OfxCreationException() {
    super("ERROR ON CREATE BALANCES FROM OFX FILE");
  }
}
