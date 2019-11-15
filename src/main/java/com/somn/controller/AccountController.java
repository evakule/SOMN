package com.somn.controller;

import com.somn.dto.AccountDTO;
import com.somn.mappers.AccountMapper;
import com.somn.model.AccountEntity;
import com.somn.model.exception.SomnLimitExceedException;
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
  @Autowired
  private AccountEntityService accountEntityService;
  
  @Autowired
  private AccountMapper accountMapper;
  
  @RequestMapping(value = "api/v1/accounts", method = RequestMethod.GET)
  public ResponseEntity<List<AccountDTO>> getAllAccounts() {
    List<AccountEntity> accountEntityList = accountEntityService.getAllAccounts();
    if (accountEntityList == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      List<AccountDTO> accountDTOList = accountMapper.toDtoList(accountEntityList);
      return new ResponseEntity<>(accountDTOList, HttpStatus.OK);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}", method = RequestMethod.GET)
  public ResponseEntity<AccountDTO> checkBalance(
      final @PathVariable("id") Long id
  ) {
    AccountEntity accountEntity = accountEntityService.getById(id);
    if (accountEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      AccountDTO accountDTO = accountMapper.toDTO(accountEntity);
      return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
  }
  
  
  @RequestMapping(value = "api/v1/accounts", method = RequestMethod.POST)
  public ResponseEntity<AccountDTO> createAccount(
      final @RequestBody AccountDTO accountDTO
  ) {
    if (accountDTO == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      AccountEntity accountEntity = accountMapper.toEntity(accountDTO);
      accountEntityService.createAccount(accountEntity);
      return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<AccountDTO> deleteAccount(
      final @PathVariable("id") Long id
  ) {
    AccountEntity accountEntity = accountEntityService.getById(id);
    if (accountEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      accountEntityService.deleteAccount(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}/withdraw",
      method = RequestMethod.PUT, params = {"amount"})
  public ResponseEntity<?> withdrawMoney(
      final @PathVariable("id") Long id,
      final @PathParam("amount") Integer amount
  ) {
    try {
      accountEntityService.withdrawMoneyFromAccount(id, amount);
    } catch (SomnLimitExceedException e) {
      return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
  @RequestMapping(value = "api/v1/accounts/{id}/deposit",
      method = RequestMethod.PUT, params = {"amount"})
  public ResponseEntity<?> depositMoney(
      final @PathVariable("id") Long id,
      final @PathParam("amount") Integer amount
  ) {
    try {
      accountEntityService.depositMoney(id, amount);
    } catch (SomnLimitExceedException e) {
      return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
