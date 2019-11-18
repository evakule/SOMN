package com.somn.service;

import com.somn.dto.UserDTO;
import com.somn.mappers.UserMapper;
import com.somn.model.UserEntity;
import com.somn.repository.UserEntityRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerEntityServiceImpl implements CustomerEntityService {
  @Autowired
  private UserEntityRepository userEntityRepository;
  
  @Autowired
  private UserMapper userMapper;
  
  @Override
  public List<UserDTO> getAllCustomers() {
    List<UserEntity> userEntityList = userEntityRepository.findAll();
    return userMapper.toDtoList(userEntityList);
  }
  
  @Override
  public void createCustomer(UserDTO userDTO) {
    UserEntity userEntity = userMapper.toEntity(userDTO);
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public UserDTO getById(Long id) {
    UserEntity userEntity = userEntityRepository.getOne(id);
    return userMapper.toDTO(userEntity);
  }
  
  @Override
  public void updateCustomer(UserDTO userDTO) {
    UserEntity userEntity = userMapper.toEntity(userDTO);
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public void deleteCustomer(Long id) {
    userEntityRepository.deleteById(id);
  }
}
