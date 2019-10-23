package com.somn.service;

import com.somn.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserEntityService {
  List<UserEntity> getAllCustomers();
  
  void createUser(UserEntity userEntity);
  
  Optional<UserEntity> getById(Long id);
  
  void updateUser(UserEntity userEntity);
  
  void deleteUser(Long id);
}
