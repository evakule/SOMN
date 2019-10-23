package com.somn.service;

import com.somn.model.UserEntity;
import com.somn.repository.UserEntityRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImpl implements UserEntityService {
  @Autowired
  private UserEntityRepository userEntityRepository;
  
  @Override
  public List<UserEntity> getAllCustomers() {
    return userEntityRepository.findAll();
  }
  
  @Override
  public void createUser(UserEntity userEntity) {
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public Optional<UserEntity> getById(Long id) {
    return userEntityRepository.findById(id);
  }
  
  @Override
  public void updateUser(UserEntity userEntity) {
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public void deleteUser(Long id) {
    userEntityRepository.deleteById(id);
  }
}
