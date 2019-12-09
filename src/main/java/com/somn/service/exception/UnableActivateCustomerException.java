package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnableActivateCustomerException extends Exception {
  
  public UnableActivateCustomerException(String message) {
    super(message);
  }
}
