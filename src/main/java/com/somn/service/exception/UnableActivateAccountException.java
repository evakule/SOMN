package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnableActivateAccountException extends Exception {
  
  public UnableActivateAccountException(String message) {
    super(message);
  }
}
