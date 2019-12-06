package com.somn.service;

import com.somn.dto.RoleDTO;
import com.somn.dto.UserDTO;
import com.somn.mappers.RoleMapper;
import com.somn.mappers.UserMapper;
import com.somn.model.RoleEntity;
import com.somn.model.UserEntity;
import com.somn.repository.RoleEntityRepository;
import com.somn.repository.UserEntityRepository;
import com.somn.service.exception.UnableDeleteAdminException;
import com.somn.service.exception.UserAlreadyExistException;

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
  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final Long ROLE_CUSTOMER_ID = 3L;
  
  @Value("${somn.user.unable-delete-admin-exception}")
  private String unableDeleteAdminMessage;
  
  @Value("${somn.user.already-exist-exception}")
  private String userAlreadyExistMessage;
  
  @Override
  public List<UserDTO> getAllCustomers() {
    List<UserEntity> userEntityList = userEntityRepository.findAll();
    return userMapper.toDtoList(userEntityList);
  }
  
  @Override
  public void createCustomer(UserDTO userDTO)
      throws UserAlreadyExistException {
    UserEntity userFromDb =
        userEntityRepository.findByFirstName(userDTO.getFirstName());
    if (userFromDb == null) {
      userDTO.setRoles(getCustomerRoleDTOFromRepo());
      UserEntity userEntity = userMapper.toEntity(userDTO);
      userEntityRepository.save(userEntity);
    } else {
      throw new UserAlreadyExistException(userAlreadyExistMessage);
    }
  }
  
  @Override
  public UserDTO getById(Long id) {
    UserEntity userEntity = userEntityRepository.getOne(id);
    return userMapper.toDTO(userEntity);
  }
  
  @Override
  public void deactivateCustomer(Long id)
      throws UnableDeleteAdminException {
    UserEntity userEntity = userEntityRepository.getOne(id);
    boolean isContainsAdminRole = userEntity.getRoles().stream()
        .anyMatch(roleEntity -> roleEntity
            .getRoleName()
            .contains(ROLE_ADMIN));
    if (isContainsAdminRole) {
      throw new UnableDeleteAdminException(unableDeleteAdminMessage);
    }
    userEntity.setUserStatus("deactivated");
    userEntityRepository.save(userEntity);
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
  
  private Set<RoleDTO> getCustomerRoleDTOFromRepo() {
    Set<RoleDTO> roleDTOSet = new HashSet<>();
    RoleEntity roleEntity = roleEntityRepository.getOne(3L);
    roleDTOSet.add(roleMapper.toDTO(roleEntity));
    return roleDTOSet;
  }
}
