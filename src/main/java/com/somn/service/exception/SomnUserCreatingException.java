package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SomnUserCreatingException extends Exception {
  
  public SomnUserCreatingException(String message) {
    super(message);
  }
}
