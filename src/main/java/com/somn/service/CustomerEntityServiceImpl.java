package com.somn.service;

import com.somn.dto.RoleDTO;
import com.somn.dto.UserDTO;
import com.somn.mappers.RoleMapper;
import com.somn.mappers.UserMapper;
import com.somn.model.RoleEntity;
import com.somn.model.UserEntity;
import com.somn.model.status.UserStatus;
import com.somn.repository.RoleEntityRepository;
import com.somn.repository.UserEntityRepository;
import com.somn.service.exception.UnableActivateCustomerException;
import com.somn.service.exception.UnableDeleteAdminException;
import com.somn.service.exception.UserAlreadyExistException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public final class CustomerEntityServiceImpl implements CustomerEntityService {
  @Value("${somn.user.unable-delete-admin-message}")
  private String unableDeleteAdminMessage;
  @Value("${somn.user.already-exist-message}")
  private String userAlreadyExistMessage;
  @Value("${somn.user.already-activated}")
  private String unableActivateCustomerMessage;
  
  private final UserEntityRepository userEntityRepository;
  private final RoleEntityRepository roleEntityRepository;
  private final UserMapper userMapper;
  private final RoleMapper roleMapper;
  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final Long ROLE_CUSTOMER_ID = 3L;
  
  @Override
  public List<UserDTO> getAllCustomers() {
    List<UserEntity> userEntityList = userEntityRepository.findAll();
    log.debug("In getAllCustomers - {} users found", userEntityList.size());
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
      log.debug(
          "In createCustomer, new customer was created, id - '{}'",
          userEntity.getId());
      userEntityRepository.save(userEntity);
    } else {
      throw new UserAlreadyExistException(userAlreadyExistMessage);
    }
  }
  
  @Override
  public UserDTO getById(Long id) {
    UserEntity userEntity = userEntityRepository.getOne(id);
    log.debug("In getById, userEntity with id - '{}', found", userEntity.getId());
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
    userEntity.setUserStatus(UserStatus.DEACTIVATED);
    log.debug(
        "In deactivateCustomer, customer with id - '{}', deactivated",
        userEntity.getId());
    userEntityRepository.save(userEntity);
  }
  
  @Override
  public void activateCustomer(Long id)
      throws UnableActivateCustomerException {
    UserEntity userEntity = userEntityRepository.getOne(id);
    if (userEntity.getUserStatus().equals(UserStatus.ACTIVE)) {
      throw new UnableActivateCustomerException(unableActivateCustomerMessage);
    }
    userEntity.setUserStatus(UserStatus.ACTIVE);
    log.debug(
        "In activateCustomer, customer with id - '{}', activated",
        userEntity.getId());
    userEntityRepository.save(userEntity);
  }
  
  
  @Override
  public UserDetails loadUserByUsername(String firstName)
      throws UsernameNotFoundException {
    UserEntity userEntity = userEntityRepository.findByFirstName(firstName);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found");
    }
    log.debug(
        "In loadUserByUsername, user with id - '{}', authenticated",
        userEntity.getId());
    return userEntity;
  }
  
  private Set<RoleDTO> getCustomerRoleDTOFromRepo() {
    Set<RoleDTO> roleDTOSet = new HashSet<>();
    RoleEntity roleEntity = roleEntityRepository.getOne(ROLE_CUSTOMER_ID);
    roleDTOSet.add(roleMapper.toDTO(roleEntity));
    return roleDTOSet;
  }
}
