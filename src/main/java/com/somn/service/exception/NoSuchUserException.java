package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoSuchUserException extends Exception {
  
  public NoSuchUserException(String message) {
    super(message);
  }
}
