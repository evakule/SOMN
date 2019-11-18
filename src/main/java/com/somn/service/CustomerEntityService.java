package com.somn.service;

import com.somn.dto.UserDTO;

import java.util.List;

public interface CustomerEntityService {
  List<UserDTO> getAllCustomers();
  
  void createCustomer(UserDTO userDTO);
  
  UserDTO getById(Long id);
  
  void updateCustomer(UserDTO userDTO);
  
  void deleteCustomer(Long id);
}
