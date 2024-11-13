package com.treasury.treasury.user.dto;

public class LoginDto {
  private String document;
  private String password;

  public LoginDto(String document, String password) {
    this.document = document;
    this.password = password;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String username) {
    this.document = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
