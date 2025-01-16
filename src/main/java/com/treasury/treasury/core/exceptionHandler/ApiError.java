package com.treasury.treasury.core.exceptionHandler;

import java.time.LocalDateTime;

public class ApiError {
  private LocalDateTime timestamp;
  private String message;
  private String error;
  private String path;
  private int status;

  public ApiError(String message, String error, String path, int status) {
    this.timestamp = LocalDateTime.now();
    this.message = message;
    this.error = error;
    this.path = path;
    this.status = status;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}
