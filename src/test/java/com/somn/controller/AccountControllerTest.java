package com.somn.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/somntest_init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/fill_in_db_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountControllerTest {
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void getAllAccounts() throws Exception {
    this.mockMvc.perform(get("http://localhost:7070/api/v1/accounts"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("[{\"id\":1,\"balance\":50050,\"accountStatus\":\"active\"}," +
            "{\"id\":2,\"balance\":50050,\"accountStatus\":\"active\"}," +
            "{\"id\":3,\"balance\":123456789,\"accountStatus\":\"active\"}]"));
  }
  
  @Test
  void checkBalance() throws Exception {
    this.mockMvc.perform(get("http://localhost:7070/api/v1/accounts/2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":2,\"balance\":50050,\"accountStatus\":\"active\"}"));
  }
  
  @Test
  void deleteAccount() throws Exception {
    //deleting object
    this.mockMvc.perform(delete("http://localhost:7070/api/v1/accounts/1"))
        .andDo(print())
        .andExpect(status().isNoContent());
    
    //checking that object was deleted
    this.mockMvc.perform(get("http://localhost:7070/api/v1/accounts"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("[{\"id\":2,\"balance\":50050,\"accountStatus\":\"active\"}," +
            "{\"id\":3,\"balance\":123456789,\"accountStatus\":\"active\"}]"));
  }
  
  @Test
  void withdrawMoney() throws Exception {
    //changing object
    this.mockMvc.perform(put("http://localhost:7070/api/v1/accounts/2/withdraw?amount=50"))
        .andDo(print())
        .andExpect(status().isNoContent());
    
    //checking that object was changed
    this.mockMvc.perform(get("http://localhost:7070/api/v1/accounts/2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":2,\"balance\":50000,\"accountStatus\":\"active\"}"));
    
  }
  
  @Test
  void depositMoney() throws Exception {
    //changing object
    this.mockMvc.perform(put("http://localhost:7070/api/v1/accounts/2/deposit?amount=50"))
        .andDo(print())
        .andExpect(status().isNoContent());
  
    //checking that object was changed
    this.mockMvc.perform(get("http://localhost:7070/api/v1/accounts/2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":2,\"balance\":50100,\"accountStatus\":\"active\"}"));
  }
}
