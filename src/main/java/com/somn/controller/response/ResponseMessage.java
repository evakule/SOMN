package com.somn.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
  ACCOUNT_CREATED("Account successfully created"),
  TRANSACTION_SUCCESS("Transaction successful"),
  
  CUSTOMER_CREATED("Customer successfully created"),
  CUSTOMER_ACTIVATED("Customer successfully activated");
  
  
  private String message;
}
