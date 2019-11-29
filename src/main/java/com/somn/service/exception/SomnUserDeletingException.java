package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SomnUserDeletingException extends Exception {
  
  public SomnUserDeletingException(String message) {
    super(message);
  }
}
