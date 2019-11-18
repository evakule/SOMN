package com.somn.controller;

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
  public ResponseEntity<List<UserDTO>> getAllCustomers() {
    List<UserDTO> userDTOList = customerEntityService.getAllCustomers();
    if (CollectionUtils.isEmpty(userDTOList)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
  }
  
  @GetMapping(value = "{id}")
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
  
  @PostMapping
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
  
  @DeleteMapping(value = "{id}")
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
