package com.somn.controller;

import com.somn.controller.response.ResponseMessage;
import com.somn.dto.UserDTO;
import com.somn.service.CustomerEntityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Api(value = "Set of endpoints for Creating, Retrieving "
    + "and Deleting of Customers",
    produces = MediaType.APPLICATION_JSON_VALUE)
public final class CustomerController {
  private static final int OK = 200;
  private static final int NOT_FOUND = 404;
  private static final int BAD_REQUEST = 400;
  private static final int CREATED = 201;
  private static final int NO_CONTENT = 204;
  
  @Autowired
  private CustomerEntityService customerEntityService;
  
  @ApiOperation("Shows all customers")
  @ApiResponses(value = {
      @ApiResponse(code = OK, message = "OK"),
      @ApiResponse(code = NOT_FOUND, message = "Not Found")
  })
  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllCustomers() {
    List<UserDTO> userDTOList = customerEntityService.getAllCustomers();
    if (CollectionUtils.isEmpty(userDTOList)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
  }
  
  @ApiOperation("Shows single customer")
  @ApiResponses(value = {
      @ApiResponse(code = OK, message = "OK"),
      @ApiResponse(code = NOT_FOUND, message = "Not Found")
  })
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
  
  @ApiOperation("Creates new customer")
  @ApiResponses(value = {
      @ApiResponse(code = CREATED, message = "Created"),
      @ApiResponse(code = BAD_REQUEST, message = "Bad Request")
  })
  @PostMapping
  public ResponseEntity<?> createNewCustomer(
      final @RequestBody UserDTO userDTO
  ) {
    if (userDTO == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      customerEntityService.createCustomer(userDTO);
      return new ResponseEntity<>(
          ResponseMessage.CUSTOMER_CREATED.getMessage(), HttpStatus.CREATED);
    }
  }
  
  @ApiOperation("Deactivates customer")
  @ApiResponses(value = {
      @ApiResponse(code = NO_CONTENT, message = "No Content"),
      @ApiResponse(code = NOT_FOUND, message = "Not Found")
  })
  @DeleteMapping(value = "{id}")
  public ResponseEntity<?> deactivateCustomer(
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
