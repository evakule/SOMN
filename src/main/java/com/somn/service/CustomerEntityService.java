package com.somn.service;

import com.somn.model.UserEntity;

import java.util.List;

public interface CustomerEntityService {
  List<UserEntity> getAllCustomers();
  
  void createCustomer(UserEntity userEntity);
  
  UserEntity getById(Long id);
  
  void updateCustomer(UserEntity userEntity);
  
  void deleteCustomer(Long id);
}
