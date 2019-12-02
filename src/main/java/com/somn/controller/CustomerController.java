package com.somn.controller;

import com.somn.controller.response.ResponseCode;
import com.somn.controller.response.ResponseMessage;
import com.somn.dto.UserDTO;
import com.somn.service.CustomerEntityService;

import com.somn.service.exception.SomnUserCreatingException;
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

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/customers")
@Api(value = "Set of endpoints for Creating, Retrieving "
    + "and Deleting of Customers",
    produces = MediaType.APPLICATION_JSON_VALUE)
public final class CustomerController {
  
  @Autowired
  private CustomerEntityService customerEntityService;
  
  @ApiOperation("Display all customers in the system except their balances. "
      + "Used only by admin.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.OK, message =
          "All customers selected successfully."),
      @ApiResponse(code = ResponseCode.NOT_FOUND, message =
          "There are no customers in system. Try to add some customer")
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
  
  @ApiOperation("Display single customer except his balance. "
      + "Used only by admin")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.OK, message =
          "Customer selected successfully"),
      @ApiResponse(code = ResponseCode.NOT_FOUND, message =
          "There is no customer associated with this id.")
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
  
  @ApiOperation("Creates single customer. Used by admin.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.CREATED, message =
          "Customer created successfully"),
      @ApiResponse(code = ResponseCode.BAD_REQUEST, message =
          "Couldn't create customer. Wrong values.")
  })
  @PostMapping
  public ResponseEntity<?> createNewCustomer(
      final @Valid @RequestBody UserDTO userDTO
  ) {
    if (userDTO == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      try {
        customerEntityService.createCustomer(userDTO);
      } catch (SomnUserCreatingException e) {
        return new ResponseEntity<>(
            e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(
          ResponseMessage.CUSTOMER_CREATED.getMessage(), HttpStatus.CREATED);
    }
  }
  
  @ApiOperation("Remove a customer. Used by admin.")
  @ApiResponses(value = {
      @ApiResponse(code = ResponseCode.NO_CONTENT, message =
          "Customer removed successfully"),
      @ApiResponse(code = ResponseCode.NOT_FOUND, message =
          "There is no customer associated with this id.")
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
