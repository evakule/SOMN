package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeactivatedAccountException extends Exception {
  
  public DeactivatedAccountException(String message) {
    super(message);
  }
}
