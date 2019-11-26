package com.somn.controller;

import com.somn.controller.response.Message;
import com.somn.dto.UserDTO;
import com.somn.service.CustomerEntityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/customers")
public final class CustomerController {
  @Autowired
  private CustomerEntityService customerEntityService;
  
  @GetMapping
  public ResponseEntity<?> getAllCustomers() {
    List<UserDTO> userDTOList = customerEntityService.getAllCustomers();
    if (CollectionUtils.isEmpty(userDTOList)) {
      return new ResponseEntity<>(
          Message.CUSTOMERS_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
  }
  
  @GetMapping(value = "{id}")
  public ResponseEntity<?> getCustomer(
      final @PathVariable("id") Long id
  ) {
    UserDTO userDTO = customerEntityService.getById(id);
    if (userDTO == null) {
      return new ResponseEntity<>(
          Message.CUSTOMERS_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
  }
  
  @PostMapping
  public ResponseEntity<?> createNewCustomer(
      final @RequestBody UserDTO userDTO
  ) {
    if (userDTO == null) {
      return new ResponseEntity<>(
          Message.CUSTOMER_CREATED, HttpStatus.BAD_REQUEST);
    } else {
      customerEntityService.createCustomer(userDTO);
      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
  }
  
  @DeleteMapping(value = "{id}")
  public ResponseEntity<?> deactivateCustomer(
      final @PathVariable("id") Long id
  ) {
    UserDTO userDTO = customerEntityService.getById(id);
    if (userDTO == null) {
      return new ResponseEntity<>(
          Message.CUSTOMERS_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      customerEntityService.deleteCustomer(id);
      return new ResponseEntity<>(
          Message.CUSTOMER_DELETED, HttpStatus.NO_CONTENT);
    }
  }
}
