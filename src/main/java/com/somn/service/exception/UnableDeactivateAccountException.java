package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnableDeactivateAccountException extends Exception {
  
  public UnableDeactivateAccountException(String message) {
    super(message);
  }
}
