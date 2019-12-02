package com.somn.service;

import com.somn.dto.RoleDTO;
import com.somn.dto.UserDTO;
import com.somn.mappers.RoleMapper;
import com.somn.mappers.UserMapper;
import com.somn.model.RoleEntity;
import com.somn.model.UserEntity;
import com.somn.repository.RoleEntityRepository;
import com.somn.repository.UserEntityRepository;
import com.somn.service.exception.SomnUserCreatingException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerEntityServiceImpl implements CustomerEntityService {
  private final UserEntityRepository userEntityRepository;
  private final RoleEntityRepository roleEntityRepository;
  private final UserMapper userMapper;
  private final RoleMapper roleMapper;
  
  @Value("${somn.user.creating-exception}")
  private String userCreatingException;
  
  @Override
  public List<UserDTO> getAllCustomers() {
    List<UserEntity> userEntityList = userEntityRepository.findAll();
    return userMapper.toDtoList(userEntityList);
  }
  
  @Override
  public void createCustomer(UserDTO userDTO)
      throws SomnUserCreatingException {
    UserEntity userFromDb =
        userEntityRepository.findByFirstName(userDTO.getFirstName());
    if (userFromDb == null) {
      Set<RoleDTO> roleDTOSet = new HashSet<>();
      roleDTOSet.add(getCustomerRoleDTOFromRepo());
      userDTO.setRoles(roleDTOSet);
      UserEntity userEntity = userMapper.toEntity(userDTO);
      userEntityRepository.save(userEntity);
    } else {
      throw new SomnUserCreatingException(userCreatingException);
    }
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
