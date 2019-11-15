package com.somn.controller;

import com.somn.dto.UserDTO;
import com.somn.mappers.UserMapper;
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
  
  @Autowired
  private UserMapper userMapper;
  
  @RequestMapping(value = "api/v1/customers", method = RequestMethod.GET)
  public ResponseEntity<List<UserDTO>> getAllCustomers() {
    List<UserEntity> userEntityList = customerEntityService.getAllCustomers();
    if (userEntityList == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      List<UserDTO> userDTOList = userMapper.toDtoList(userEntityList);
      return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
  }
  
  @RequestMapping(value = "api/v1/customers/{id}", method = RequestMethod.GET)
  public ResponseEntity<UserDTO> getCustomer(
      final @PathVariable("id") Long id
  ) {
    UserEntity userEntity = customerEntityService.getById(id);
    if (userEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      UserDTO userDTO = userMapper.toDTO(userEntity);
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
      UserEntity userEntity = userMapper.toEntity(userDTO);
      customerEntityService.createCustomer(userEntity);
      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
  }
  
  @RequestMapping(value = "api/v1/customers/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<UserDTO> deactivateUser(
      final @PathVariable("id") Long id
  ) {
    UserEntity userEntity = customerEntityService.getById(id);
    if (userEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      customerEntityService.deleteCustomer(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
