package com.somn.controller;

import com.somn.dto.AccountDTO;
import com.somn.exception.SomnLimitExceedException;
import com.somn.model.UserEntity;
import com.somn.service.AccountEntityService;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/accounts")
public class AccountController {
  @Autowired
  private AccountEntityService accountEntityService;
  
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @GetMapping(value = "/all")
  public ResponseEntity<List<AccountDTO>> getAllAccounts() {
    List<AccountDTO> accountDTOList = accountEntityService.getAllAccountsWithoutBalance();
    if (CollectionUtils.isEmpty(accountDTOList)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(accountDTOList, HttpStatus.OK);
    }
  }
  
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @GetMapping(value = "{id}")
  public ResponseEntity<AccountDTO> getAccount(
      final @PathVariable("id") Long id
  ) {
    AccountDTO accountDTO = accountEntityService.getAccountByIdWithoutBalance(id);
    if (accountDTO == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
  }
  
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @PostMapping
  public ResponseEntity<AccountDTO> createAccount(
      final @RequestBody AccountDTO accountDTO
  ) {
    if (accountDTO == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      accountEntityService.createAccount(accountDTO);
      return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }
  }
  
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @DeleteMapping(value = "{id}")
  public ResponseEntity<AccountDTO> deleteAccount(
      final @PathVariable("id") Long id
  ) {
    AccountDTO accountDTO = accountEntityService.getById(id);
    if (accountDTO == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      accountEntityService.deleteAccount(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
  
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PutMapping(value = "{id}/withdraw", params = {"amount"})
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
  
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PutMapping(value = "{id}/deposit", params = {"amount"})
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
  
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping
  public ResponseEntity<List<AccountDTO>> checkBalanceByCustomer() {
    List<AccountDTO> accountDTOList = accountEntityService
        .getAllCustomerAccountsById(getUserIdFromSession());
    if (CollectionUtils.isEmpty(accountDTOList)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(accountDTOList, HttpStatus.OK);
    }
  }
  
  private static Long getUserIdFromSession() {
    Authentication authentication = SecurityContextHolder
        .getContext()
        .getAuthentication();
    UserEntity userEntity = (UserEntity) authentication.getPrincipal();
    return userEntity.getId();
  }
}
