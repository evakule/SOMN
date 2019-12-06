package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SomnLimitExceedException extends Exception {
  
  public SomnLimitExceedException(String message) {
    super(message);
  }
}
