package com.somn.service;

import com.somn.dto.RoleDTO;
import com.somn.dto.UserDTO;
import com.somn.mappers.RoleMapper;
import com.somn.mappers.UserMapper;
import com.somn.model.RoleEntity;
import com.somn.model.UserEntity;
import com.somn.repository.RoleEntityRepository;
import com.somn.repository.UserEntityRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerEntityServiceImpl implements CustomerEntityService {
  @Autowired
  private UserEntityRepository userEntityRepository;
  
  @Autowired
  private RoleEntityRepository roleEntityRepository;
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private RoleMapper roleMapper;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  public List<UserDTO> getAllCustomers() {
    List<UserEntity> userEntityList = userEntityRepository.findAll();
    return userMapper.toDtoList(userEntityList);
  }
  
  @Override
  public void createCustomer(UserDTO userDTO) {
    Set<RoleDTO> roleDTOSet = new HashSet<>();
    roleDTOSet.add(getCustomerRoleDTOFromRepo());
    userDTO.setRoles(roleDTOSet);
    UserEntity userEntity = userMapper.toEntity(userDTO);
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public UserDTO getById(Long id) {
    UserEntity userEntity = userEntityRepository.getOne(id);
    return userMapper.toDTO(userEntity);
  }
  
  @Override
  public void deleteCustomer(Long id) {
    userEntityRepository.deleteById(id);
  }
  
  @Override
  public UserDetails loadUserByUsername(String firstName)
      throws UsernameNotFoundException {
    UserEntity userEntity = userEntityRepository.findByFirstName(firstName);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return userEntity;
  }
  
  private RoleDTO getCustomerRoleDTOFromRepo() {
    RoleEntity roleEntity = roleEntityRepository.getOne(3L);
    return roleMapper.toDTO(roleEntity);
  }
}
