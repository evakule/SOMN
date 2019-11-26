package com.somn.controller;

import com.somn.controller.response.Message;
import com.somn.dto.AccountantAccountDTO;
import com.somn.dto.CustomerAccountDTO;
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
  public ResponseEntity<?> getAllAccounts() {
    List<AccountantAccountDTO> accountantAccountDTOList =
        accountEntityService.getAllAccounts();
    if (CollectionUtils.isEmpty(accountantAccountDTOList)) {
      return new ResponseEntity<>(
          Message.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(accountantAccountDTOList, HttpStatus.OK);
    }
  }
  
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @GetMapping(value = "{id}")
  public ResponseEntity<?> getAccount(
      final @PathVariable("id") Long id
  ) {
    AccountantAccountDTO accountantAccountDTO =
        accountEntityService.getById(id);
    if (accountantAccountDTO == null) {
      return new ResponseEntity<>(
          Message.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(accountantAccountDTO, HttpStatus.OK);
    }
  }
  
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @PostMapping
  public ResponseEntity<?> createAccount(
      final @RequestBody CustomerAccountDTO customerAccountDTO
  ) {
    try {
      if (customerAccountDTO == null) {
        return new ResponseEntity<>(
            Message.INCORRECT_VALUES, HttpStatus.BAD_REQUEST);
      } else {
        accountEntityService.createAccount(customerAccountDTO);
        return new ResponseEntity<>(
            Message.ACCOUNT_CREATED, HttpStatus.CREATED);
      }
    } catch (SomnLimitExceedException e) {
      return new ResponseEntity<>(
          e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
  }
  
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @DeleteMapping(value = "{id}")
  public ResponseEntity<?> deleteAccount(
      final @PathVariable("id") Long id
  ) {
    AccountantAccountDTO accountantAccountDTO =
        accountEntityService.getById(id);
    if (accountantAccountDTO == null) {
      return new ResponseEntity<>(
          Message.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      accountEntityService.deleteAccount(id);
      return new ResponseEntity<>(
          Message.ACCOUNT_DELETED, HttpStatus.NO_CONTENT);
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
      return new ResponseEntity<>(
          e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        Message.ACCOUNT_TRANSACTION_SUCCESS, HttpStatus.OK);
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
      return new ResponseEntity<>(
          e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        Message.ACCOUNT_TRANSACTION_SUCCESS, HttpStatus.OK);
  }
  
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping
  public ResponseEntity<?> checkBalanceByCustomer() {
    List<CustomerAccountDTO> customerAccountDTOList = accountEntityService
        .getAllCustomerAccountsById(getUserIdFromSession());
    if (CollectionUtils.isEmpty(customerAccountDTOList)) {
      return new ResponseEntity<>(
          Message.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(
          customerAccountDTOList, HttpStatus.OK);
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
