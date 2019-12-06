package com.somn.service;

import com.somn.dto.UserDTO;

import com.somn.service.exception.UnableDeleteAdminException;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerEntityService extends UserDetailsService {
  List<UserDTO> getAllCustomers();
  
  void createCustomer(UserDTO userDTO);
  
  UserDTO getById(Long id);
  
  void deactivateCustomer(Long id) throws UnableDeleteAdminException;
}
