package com.somn.service;

import com.somn.model.AccountEntity;
import com.somn.model.RoleEntity;
import com.somn.model.UserEntity;
import com.somn.model.status.AccountStatus;
import com.somn.model.status.UserStatus;
import com.somn.service.exception.SomnLimitExceedException;
import com.somn.repository.AccountEntityRepository;
import com.somn.service.exception.UnableActivateAccountException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountEntityServiceImplTest {
  @MockBean
  private AccountEntityRepository mockRepository;
  
  @Autowired
  private AccountEntityService accountEntityService;
  
  private AccountEntity accountEntity;
  private UserEntity userEntity;
  private Set<RoleEntity> roleEntities;
  
  @Before
  public void getRole() {
    roleEntities = new HashSet<>();
    RoleEntity roleEntity = new RoleEntity("ROLE_ADMIN");
    roleEntities.add(roleEntity);
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
  public void getAccount() {
    accountEntity = new AccountEntity(50050, AccountStatus.ACTIVE, userEntity);
  }
  
  @Test(expected = SomnLimitExceedException.class)
  public void withdrawMoneyFromAccountNegativeScenarios() throws SomnLimitExceedException {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    accountEntityService.withdrawMoneyFromAccount(1L, 9);
    accountEntityService.withdrawMoneyFromAccount(1L, 50051);
  }
  
  @Test(expected = SomnLimitExceedException.class)
  public void depositMoneyNegativeScenarios() throws SomnLimitExceedException {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    accountEntityService.depositMoney(1L, 9);
    accountEntityService.depositMoney(1L, 1000000);
  }
  
  @Test(expected = UnableActivateAccountException.class)
  public void activateAccountNegativeScenario() throws UnableActivateAccountException {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    Mockito.when(mockRepository.save(accountEntity)).thenReturn(accountEntity);
    accountEntityService.activateAccount(1L);
  }
  
  @Test
  public void withdrawMoneyFromAccountPositiveScenario() throws SomnLimitExceedException {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    Mockito.when(mockRepository.save(accountEntity)).thenReturn(accountEntity);
    accountEntityService.withdrawMoneyFromAccount(1L, 100);
    Assertions.assertEquals(49950, mockRepository.getOne(1L).getBalance());
  }
  
  @Test
  public void depositMoneyPositiveScenario() throws SomnLimitExceedException {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    Mockito.when(mockRepository.save(accountEntity)).thenReturn(accountEntity);
    accountEntityService.depositMoney(1L, 100);
    Assertions.assertEquals(50150, mockRepository.getOne(1L).getBalance());
  }
  
  @Test
  public void activateAccountPositiveScenario() throws UnableActivateAccountException {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    Mockito.when(mockRepository.save(accountEntity)).thenReturn(accountEntity);
    accountEntity.setAccountStatus(AccountStatus.DEACTIVATED);
    accountEntityService.activateAccount(1L);
  }
}
