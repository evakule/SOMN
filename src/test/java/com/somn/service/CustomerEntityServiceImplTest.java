package com.somn.service;

import com.somn.dto.RoleDTO;
import com.somn.dto.UserDTO;
import com.somn.model.RoleEntity;
import com.somn.model.UserEntity;
import com.somn.model.status.UserStatus;
import com.somn.repository.UserEntityRepository;
import com.somn.service.exception.UnableActivateCustomerException;
import com.somn.service.exception.UnableDeleteAdminException;
import com.somn.service.exception.UserAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerEntityServiceImplTest {
  @MockBean
  private UserEntityRepository mockRepository;
  
  @Autowired
  private CustomerEntityService customerEntityService;
  
  private UserEntity userEntity;
  private UserDTO userDTO;
  private HashSet<RoleEntity> roleEntities;
  private HashSet<RoleDTO> roleDTOSet;
  
  @Before
  public void getRole() {
    roleEntities = new HashSet<>();
    roleDTOSet = new HashSet<>();
    RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");
    roleEntities.add(roleEntity);
    RoleDTO roleDTO = new RoleDTO(1L, "ROLE_ADMIN");
    roleDTOSet.add(roleDTO);
  }
  
  @Before
  public void getCustomer() {
    userEntity = new UserEntity("Egor",
        "1234",
        UserStatus.ACTIVE,
        roleEntities,
        new ArrayList<>()
    );
  }
  
  @Before
  public void getUserDto() {
    userDTO = new UserDTO(
        1L,
        "Egor",
        "123456",
        UserStatus.ACTIVE,
        roleDTOSet
    );
  }
  
  @Test(expected = UserAlreadyExistException.class)
  public void createCustomerNegativeScenario() throws UserAlreadyExistException {
    Mockito.when(mockRepository.findByFirstName(userDTO.getFirstName())).thenReturn(userEntity);
    Mockito.when(mockRepository.save(userEntity)).thenReturn(userEntity);
    customerEntityService.createCustomer(userDTO);
  }
  
  @Test(expected = UnableDeleteAdminException.class)
  public void deactivateCustomerNegativeScenario() throws UnableDeleteAdminException {
    Set<RoleEntity> roleEntities = new HashSet<>();
    roleEntities.add(new RoleEntity("ROLE_ADMIN"));
    userEntity.setRoles(roleEntities);
    Mockito.when(mockRepository.getOne(1L)).thenReturn(userEntity);
    Mockito.when(mockRepository.save(userEntity)).thenReturn(userEntity);
    customerEntityService.deactivateCustomer(1L);
  }
  
  @Test(expected = UnableActivateCustomerException.class)
  public void activateCustomerNegativeScenario() throws UnableActivateCustomerException {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(userEntity);
    Mockito.when(mockRepository.save(userEntity)).thenReturn(userEntity);
    customerEntityService.activateCustomer(1L);
  }
  
  @Test
  @Transactional // Spring enable session management, and test will run
  public void createCustomerPositiveScenario() throws UserAlreadyExistException {
    userEntity.setFirstName("Oleg");
    userDTO.setFirstName("Oleg");
    Mockito.when(mockRepository.findByFirstName("Oleg")).thenReturn(null);
    Mockito.when(mockRepository.save(userEntity)).thenReturn(userEntity);
    customerEntityService.createCustomer(userDTO);
  }
  
  @Test
  public void deactivateCustomerPositiveScenario() throws UnableDeleteAdminException {
    RoleEntity roleEntity = new RoleEntity("ROLE_CUSTOMER");
    roleEntities.clear();
    roleEntities.add(roleEntity);
    userEntity.setRoles(roleEntities);
    Mockito.when(mockRepository.getOne(1L)).thenReturn(userEntity);
    Mockito.when(mockRepository.save(userEntity)).thenReturn(userEntity);
    customerEntityService.deactivateCustomer(1L);
  }
  
  @Test
  public void activateCustomerPositiveScenario() throws UnableActivateCustomerException {
    userEntity.setUserStatus(UserStatus.DEACTIVATED);
    Mockito.when(mockRepository.getOne(1L)).thenReturn(userEntity);
    Mockito.when(mockRepository.save(userEntity)).thenReturn(userEntity);
    customerEntityService.activateCustomer(1L);
  }
}
