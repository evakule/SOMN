package com.somn.service;

import com.somn.model.UserEntity;
import com.somn.repository.UserEntityRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerEntityServiceImpl implements CustomerEntityService {
  @Autowired
  private UserEntityRepository userEntityRepository;
  
  @Override
  public Optional<List<UserEntity>> getAllCustomers() {
    return Optional.ofNullable(userEntityRepository.findAll());
  }
  
  @Override
  public void createCustomer(UserEntity userEntity) {
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public Optional<UserEntity> getById(Long id) {
    return userEntityRepository.findById(id);
  }
  
  @Override
  public void updateCustomer(UserEntity userEntity) {
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public void deleteCustomer(Long id) {
    userEntityRepository.deleteById(id);
  }
}
