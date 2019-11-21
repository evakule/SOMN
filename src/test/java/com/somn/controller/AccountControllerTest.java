package com.somn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somn.model.AccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
@WithMockUser(roles = {"ACCOUNTANT"})
class AccountControllerTest {
  @Autowired
  private MockMvc mockMvc;
  
  private AccountEntity accountEntity = new AccountEntity(1000, "active");
  
  @Test
  void getAllAccounts() throws Exception {
    this.mockMvc.perform(get("/api/v1/accounts"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("[{\"id\":1,\"balance\":50050,\"accountStatus\":\"active\"}," +
            "{\"id\":2,\"balance\":50050,\"accountStatus\":\"active\"}," +
            "{\"id\":3,\"balance\":123456789,\"accountStatus\":\"active\"}]"));
  }
  
  @Test
  void checkBalance() throws Exception {
    this.mockMvc.perform(get("/api/v1/accounts/2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":2,\"balance\":50050,\"accountStatus\":\"active\"}"));
  }
  
  @Test
  void createAccount() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
        .with(csrf())
        .content(new ObjectMapper().writeValueAsString(accountEntity))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());
    
    //checking that object was created
    this.mockMvc.perform(get("/api/v1/accounts/4"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":4,\"balance\":1000,\"accountStatus\":\"active\"}"));
  }
  
  @Test
  void deleteAccount() throws Exception {
    //deleting object
    this.mockMvc.perform(delete("/api/v1/accounts/1")
        .with(csrf()))
        .andDo(print())
        .andExpect(status().isNoContent());
    
    //checking that object was deleted
    this.mockMvc.perform(get("/api/v1/accounts"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("[{\"id\":2,\"balance\":50050,\"accountStatus\":\"active\"}," +
            "{\"id\":3,\"balance\":123456789,\"accountStatus\":\"active\"}]"));
  }
  
  @Test
  void withdrawMoney() throws Exception {
    //changing object
    this.mockMvc.perform(put("/api/v1/accounts/2/withdraw?amount=50")
        .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk());
    
    //checking that object was changed
    this.mockMvc.perform(get("/api/v1/accounts/2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":2,\"balance\":50000,\"accountStatus\":\"active\"}"));
    
  }
  
  @Test
  void depositMoney() throws Exception {
    //changing object
    this.mockMvc.perform(put("/api/v1/accounts/2/deposit?amount=50")
        .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk());
    
    //checking that object was changed
    this.mockMvc.perform(get("/api/v1/accounts/2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":2,\"balance\":50100,\"accountStatus\":\"active\"}"));
  }
}
