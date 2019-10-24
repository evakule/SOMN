package com.somn.controller;

import com.somn.model.AccountEntity;
import com.somn.service.AccountEntityService;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class AccountController {
  private static final Integer OPERATION_LIMIT = 10;
  private static final Integer BALANCE_LIMIT = 1000000;
  
  @Autowired
  private AccountEntityService accountEntityService;
  
  @RequestMapping(value = "api/v1/accounts", method = RequestMethod.GET)
  public ResponseEntity<List<AccountEntity>> getAllAccounts() {
    return accountEntityService.getAllAccounts().map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}", method = RequestMethod.GET)
  public ResponseEntity<AccountEntity> checkBalance(final @PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return accountEntityService.getById(id).map(ResponseEntity::ok)
          .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts", method = RequestMethod.POST)
  public ResponseEntity<AccountEntity> createAccount(final @RequestBody AccountEntity accountEntity) {
    if (accountEntity == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      accountEntityService.createAccount(accountEntity);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<AccountEntity> deleteAccount(final @PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      accountEntityService.deleteAccount(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}/withdraw", method = RequestMethod.PUT, params = {"amount"})
  public ResponseEntity<AccountEntity> withdrawMoney(
      final @PathVariable("id") Long id,
      final @PathParam("amount") Integer amount) {
    AccountEntity accountEntity = accountEntityService.getById(id).get();
    Integer oldBalance = accountEntity.getBalance();
    if (amount > oldBalance || amount < OPERATION_LIMIT) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      Integer newBalance = oldBalance - amount;
      accountEntity.setBalance(newBalance);
      accountEntityService.updateAccount(accountEntity);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}/deposit", method = RequestMethod.PUT, params = {"amount"})
  public ResponseEntity<AccountEntity> depositMoney(
      final @PathVariable("id") Long id,
      final @PathParam("amount") Integer amount) {
    AccountEntity accountEntity = accountEntityService.getById(id).get();
    Integer oldBalance = accountEntity.getBalance();
    if (amount + oldBalance > BALANCE_LIMIT || amount < OPERATION_LIMIT) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      Integer newBalance = oldBalance + amount;
      accountEntity.setBalance(newBalance);
      accountEntityService.updateAccount(accountEntity);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}