package com.somn.service.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnableDeleteAdminException extends Exception {
  
  public UnableDeleteAdminException(String message) {
    super(message);
  }
}
