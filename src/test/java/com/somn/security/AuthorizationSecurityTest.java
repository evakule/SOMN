package com.somn.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/somntest_init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/fill_in_db_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthorizationTest {
  @Autowired
  private MockMvc mockMvc;
  
  @WithMockUser(roles = {"ACCOUNTANT"})
  @Test
  void getCustomer() throws Exception {
    this.mockMvc.perform(get("/api/v1/customers/2"))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
  
  @WithMockUser(roles = {"ADMIN"})
  @Test
  void checkBalance() throws Exception {
    this.mockMvc.perform(get("/api/v1/accounts/2"))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
