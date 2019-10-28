package com.somn.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/somntest_init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/fill_in_db_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountEntityServiceImplTest {
  @Value("${limit.operation}")
  private Integer operationLimit;
  @Value("${limit.balance}")
  private Integer balanceLimit;
  @Autowired
  private AccountEntityServiceImpl accountEntityService;
  
  @Before
  private Integer getAmount() {
    return 100;
  }
  
  @Test
  void withdrawMoneyFromAccount() {
    accountEntityService.withdrawMoneyFromAccount(1L, getAmount(), operationLimit, balanceLimit);
    assertEquals(49950, accountEntityService.getById(1L).get().getBalance());
  }
  
  @Test
  void replenishAccount() {
    accountEntityService.replenishAccount(1L, getAmount(), operationLimit, balanceLimit);
    assertEquals(50150, accountEntityService.getById(1L).get().getBalance());
  }
}