package com.treasury.treasury.user.schema;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.treasury.treasury.user.constants.UserRoles;

@Document(collection = "user")
public class User {
  @Id
  private String id;
  private String name;
  private String email;
  private String document;
  private Boolean isActive;
  private UserRoles role;
  private Instant createdAt;
  private Instant updatedAt;
  private String password;


  public User(String name, String email, String document, UserRoles role, String password) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.email = email;
    this.document = document;
    this.isActive = true;
    this.role = role;
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.updatedAt = Instant.now();
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.updatedAt = Instant.now();
    this.email = email;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.updatedAt = Instant.now();
    this.document = document;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.updatedAt = Instant.now();
    this.isActive = isActive;
  }

  public UserRoles getRole() {
    return role;
  }

  public void setRole(UserRoles role) {
    this.updatedAt = Instant.now();
    this.role = role;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
