package com.somn.service;

import com.somn.model.AccountEntity;
import com.somn.repository.AccountEntityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountEntityServiceImplTest {
  @MockBean
  private AccountEntityRepository mockRepository;
  
  @Autowired
  private AccountEntityService accountEntityService;
  
  private AccountEntity accountEntity;
  
  @Before
  public void getAccount() {
    accountEntity = new AccountEntity(50050, "active");
  }
  
  @Test(expected = RuntimeException.class)
  public void withdrawMoneyFromAccount() {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    accountEntityService.withdrawMoneyFromAccount(1L, 9);
    accountEntityService.withdrawMoneyFromAccount(1L, 50051);
  }
  
  @Test(expected = RuntimeException.class)
  public void depositMoney() {
    Mockito.when(mockRepository.getOne(1L)).thenReturn(accountEntity);
    accountEntityService.depositMoney(1L, 9);
    accountEntityService.depositMoney(1L, 1000000);
  }
}
