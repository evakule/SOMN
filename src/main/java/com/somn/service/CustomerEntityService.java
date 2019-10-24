package com.somn.service;

import com.somn.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerEntityService {
  Optional<List<UserEntity>> getAllCustomers();
  
  void createCustomer(UserEntity userEntity);
  
  Optional<UserEntity> getById(Long id);
  
  void updateCustomer(UserEntity userEntity);
  
  void deleteCustomer(Long id);
}
