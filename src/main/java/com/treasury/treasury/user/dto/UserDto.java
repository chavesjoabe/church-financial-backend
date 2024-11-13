package com.treasury.treasury.user.dto;

import com.treasury.treasury.user.constants.UserRoles;

public class UserDto {
  private String name;
  private String email;
  private String document;
  private UserRoles role;
  private String password;

  public UserDto(String name, String email, String document, UserRoles role, String password) {
    this.name = name;
    this.email = email;
    this.document = document;
    this.role = role;
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public UserRoles getRole() {
    return role;
  }

  public void setRole(UserRoles role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
