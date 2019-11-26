package com.somn.controller.response;

public enum ResponseMessage {
  ACCOUNT_CREATED("Account successfully created"),
  ACCOUNT_DELETED("Account deleted"),
  ACCOUNT_TRANSACTION_SUCCESS("Transaction successful"),
  
  
  CUSTOMER_CREATED("Customer successfully created"),
  CUSTOMER_DELETED("Customer was deleted");
  
  private String message;
  
  private ResponseMessage(String message) {
    this.message = message;
  }
  
  public static String accountCreated() {
    return ACCOUNT_CREATED.message;
  }
  
  public static String accountDeleted() {
    return ACCOUNT_DELETED.message;
  }
  
  public static String transactionSuccess() {
    return ACCOUNT_TRANSACTION_SUCCESS.message;
  }
  
  public static String customerCreated() {
    return CUSTOMER_CREATED.message;
  }
  
  public static String customerDeleted() {
    return CUSTOMER_DELETED.message;
  }
}
