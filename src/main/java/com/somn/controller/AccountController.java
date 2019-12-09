package com.somn.controller;

import com.somn.controller.response.ResponseCode;
import com.somn.controller.response.ResponseMessage;
import com.somn.dto.AccountantAccountDTO;
import com.somn.dto.CustomerAccountDTO;
import com.somn.model.UserEntity;
import com.somn.service.AccountEntityService;
import com.somn.service.exception.NoSuchUserException;
import com.somn.service.exception.SomnLimitExceedException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Api(value = "Set of endpoints for Creating, Retrieving, "
    + "Updating and Deleting of Accounts",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
  
  @Autowired
  private AccountEntityService accountEntityService;
  
  @ApiOperation("Display all accounts in the system. Used only by accountant.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.OK, message =
          "All accounts selected successfully."),
      @ApiResponse(code = ResponseCode.NOT_FOUND, message =
          "There are no accounts associated with any user.")
  })
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @GetMapping(value = "/all")
  public ResponseEntity<List<AccountantAccountDTO>> getAllAccounts() {
    List<AccountantAccountDTO> accountantAccountDTOList =
        accountEntityService.getAllAccounts();
    if (CollectionUtils.isEmpty(accountantAccountDTOList)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(accountantAccountDTOList, HttpStatus.OK);
    }
  }
  
  @ApiOperation("Display single customer account except balance. "
      + "Used only by accountant")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.OK, message =
          "Account selected successfully."),
      @ApiResponse(code = ResponseCode.NOT_FOUND, message =
          "There is no account associated with this id.")
  })
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @GetMapping(value = "{id}")
  public ResponseEntity<AccountantAccountDTO> getAccount(
      final @PathVariable("id") Long id
  ) {
    AccountantAccountDTO accountantAccountDTO =
        accountEntityService.getById(id);
    if (accountantAccountDTO == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(accountantAccountDTO, HttpStatus.OK);
    }
  }
  
  @ApiOperation("Creates single account. Used by accountant.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.CREATED, message =
          "Account created successfully"),
      @ApiResponse(code = ResponseCode.BAD_REQUEST, message =
          "Couldn't create an account. Wrong values.")
  })
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @PostMapping
  public ResponseEntity<?> createAccount(
      final @Valid @RequestBody AccountantAccountDTO accountantAccountDTO
  ) {
    if (accountantAccountDTO == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      accountEntityService.createAccount(accountantAccountDTO);
    } catch (NoSuchUserException e) {
      return new ResponseEntity<>(
          e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        ResponseMessage.ACCOUNT_CREATED.getMessage(), HttpStatus.CREATED);
    
  }
  
  @ApiOperation("Remove an account. Used by accountant.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.NO_CONTENT, message =
          "Account removed successfully"),
      @ApiResponse(code = ResponseCode.NOT_FOUND, message =
          "There is no account associated with this id.")
  })
  @PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
  @DeleteMapping(value = "{id}")
  public ResponseEntity<?> deleteAccount(
      final @PathVariable("id") Long id
  ) {
    AccountantAccountDTO accountantAccountDTO =
        accountEntityService.getById(id);
    if (accountantAccountDTO == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      accountEntityService.deleteAccount(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
  
  @ApiOperation("Withdraw money from account. Used by customer.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.OK, message =
          "Transaction successful"),
      @ApiResponse(code = ResponseCode.BAD_REQUEST, message =
          "Couldn't make transaction. Make sure that transaction value "
              + "more than minimum operation value and amount of money "
              + "you want to withdraw more than your balance sum")
  })
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
        ResponseMessage.TRANSACTION_SUCCESS.getMessage(), HttpStatus.OK);
  }
  
  @ApiOperation("Deposit money on account. Used by customer.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.OK, message =
          "Transaction successful"),
      @ApiResponse(code = ResponseCode.BAD_REQUEST, message =
          "Couldn't make transaction. Make sure that transaction value "
              + "more than minimum operation value, account balance and "
              + "deposit amount that you want to put into the account "
              + "in total not exceeding balance store limit")
  })
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
        ResponseMessage.TRANSACTION_SUCCESS.getMessage(), HttpStatus.OK);
  }
  
  @ApiOperation("Shows all accounts of the customer. Used by customer.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.OK, message =
          "All accounts selected successfully."),
      @ApiResponse(code = ResponseCode.NOT_FOUND, message =
          "There is no accounts associated with you")
  })
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping
  public ResponseEntity<List<CustomerAccountDTO>> checkBalanceByCustomer() {
    List<CustomerAccountDTO> customerAccountDTOList = accountEntityService
        .getAllCustomerAccountsById(getUserIdFromSession());
    if (CollectionUtils.isEmpty(customerAccountDTOList)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
