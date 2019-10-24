package com.somn.controller;

import com.somn.model.UserEntity;
import com.somn.service.CustomerEntityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class CustomerController {
  
  @Autowired
  private CustomerEntityService customerEntityService;
  
  @RequestMapping(value = "api/v1/customers", method = RequestMethod.GET)
  public ResponseEntity<List<UserEntity>> getAllCustomers() {
    return customerEntityService.getAllCustomers().map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
  }
  
  @RequestMapping(value = "api/v1/customers", method = RequestMethod.POST)
  public ResponseEntity<UserEntity> createNewCustomer(final @RequestBody UserEntity userEntity) {
    if (userEntity == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      customerEntityService.createCustomer(userEntity);
      return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
  }
  
  @RequestMapping(value = "api/v1/customers/{id}", method = RequestMethod.GET)
  public ResponseEntity<UserEntity> getCustomer(final @PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return customerEntityService.getById(id).map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
  }
  
  @RequestMapping(value = "api/v1/customers/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<UserEntity> deactivateUser(final @PathVariable("id") Long id) {
    if (id == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      customerEntityService.deleteCustomer(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
