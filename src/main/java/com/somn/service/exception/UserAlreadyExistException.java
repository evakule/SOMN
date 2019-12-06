package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAlreadyExistException extends Exception {
  
  public UserAlreadyExistException(String message) {
    super(message);
  }
}
