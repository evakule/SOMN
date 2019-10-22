package com.somn.controller;

import com.somn.model.AccountEntity;
import com.somn.service.AccountEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
  
  @Autowired
  private AccountEntityService accountEntityService;
  
  @RequestMapping(value = "api/v1/accounts", method = RequestMethod.GET)
  public ResponseEntity<List<AccountEntity>> getAllAccounts() {
    return accountEntityService.getAllAccounts().map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}", method = RequestMethod.GET)
  public ResponseEntity<AccountEntity> checkBalance(@PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return accountEntityService.getById(id).map(ResponseEntity::ok)
          .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts", method = RequestMethod.POST)
  public ResponseEntity<AccountEntity> createAccount(@RequestBody AccountEntity accountEntity) {
    if (accountEntity == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      accountEntityService.createAccount(accountEntity);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<AccountEntity> deleteAccount(@PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      accountEntityService.deleteAccount(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
