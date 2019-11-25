package com.somn.service;

import com.somn.dto.CustomerAccountDTO;
import com.somn.model.AccountEntity;
import com.somn.exception.SomnLimitExceedException;
import com.somn.repository.AccountEntityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
  
  private CustomerAccountDTO negativeCustomerAccountDTO =
      new CustomerAccountDTO(
          4L, 1000001, "active", 2L
      );
  
  private CustomerAccountDTO positiveCustomerAccountDTO =
      new CustomerAccountDTO(
          4L, 50050, "active", 2L
      );
  
  @Before
  public void getAccount() {
    accountEntity = new AccountEntity(50050, "active");
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
  
  @Test(expected = SomnLimitExceedException.class)
  public void createAccountNegativeScenario() throws SomnLimitExceedException {
    Mockito.when(mockRepository.save(accountEntity)).thenReturn(accountEntity);
    Mockito.when(mockRepository.getOne(4L)).thenReturn(accountEntity);
    accountEntityService.createAccount(negativeCustomerAccountDTO);
    Assertions.assertEquals(accountEntity, mockRepository.getOne(4L));
  }
  
  @Test
  public void createAccountPositiveScenario() throws SomnLimitExceedException {
    Mockito.when(mockRepository.save(accountEntity)).thenReturn(accountEntity);
    Mockito.when(mockRepository.getOne(4L)).thenReturn(accountEntity);
    accountEntityService.createAccount(positiveCustomerAccountDTO);
    Assertions.assertEquals(accountEntity, mockRepository.getOne(4L));
  }
}
