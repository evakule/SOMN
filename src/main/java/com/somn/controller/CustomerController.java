package com.somn.controller;

import com.somn.dto.UserDTO;
import com.somn.service.CustomerEntityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
  public ResponseEntity<List<UserDTO>> getAllCustomers() {
    List<UserDTO> userDTOList = customerEntityService.getAllCustomers();
    if (CollectionUtils.isEmpty(userDTOList)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
  }
  
  @RequestMapping(value = "api/v1/customers/{id}", method = RequestMethod.GET)
  public ResponseEntity<UserDTO> getCustomer(
      final @PathVariable("id") Long id
  ) {
    UserDTO userDTO = customerEntityService.getById(id);
    if (userDTO == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
  }
  
  @RequestMapping(value = "api/v1/customers", method = RequestMethod.POST)
  public ResponseEntity<UserDTO> createNewCustomer(
      final @RequestBody UserDTO userDTO
  ) {
    if (userDTO == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      customerEntityService.createCustomer(userDTO);
      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
  }
  
  @RequestMapping(value = "api/v1/customers/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<UserDTO> deactivateUser(
      final @PathVariable("id") Long id
  ) {
    UserDTO userDTO = customerEntityService.getById(id);
    if (userDTO == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      customerEntityService.deleteCustomer(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
