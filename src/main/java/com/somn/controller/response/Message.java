package com.somn.controller.response;

public enum Message {
  ACCOUNT_NOT_FOUND("Accounts not found"),
  INCORRECT_VALUES("Incorrect values for creation new account"),
  ACCOUNT_CREATED("Account successfully created"),
  ACCOUNT_DELETED("Account deleted"),
  ACCOUNT_TRANSACTION_SUCCESS("Transaction successful"),
  
  
  CUSTOMERS_NOT_FOUND("Customers not found"),
  CUSTOMER_CREATED("Customer successfully created"),
  CUSTOMER_DELETED("Customer was deleted");
  
  private String message;
  
  private Message(String message) {
    this.message = message;
  }
}
