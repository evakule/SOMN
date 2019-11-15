package com.somn.service;

import com.somn.model.UserEntity;
import com.somn.repository.UserEntityRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerEntityServiceImpl implements CustomerEntityService {
  @Autowired
  private UserEntityRepository userEntityRepository;
  
  @Override
  public List<UserEntity> getAllCustomers() {
    return userEntityRepository.findAll();
  }
  
  @Override
  public void createCustomer(UserEntity userEntity) {
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public UserEntity getById(Long id) {
    return userEntityRepository.getOne(id);
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
