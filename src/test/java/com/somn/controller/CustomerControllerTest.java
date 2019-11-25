package com.somn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somn.model.UserEntity;
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

import java.util.ArrayList;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@WithMockUser(roles = {"ADMIN"})
class CustomerControllerTest {
  @Autowired
  private MockMvc mockMvc;
  
  private UserEntity userEntity = new UserEntity(
      "Alex",
      "$2a$04$0bFPDA4bcQApDiCTJGl0mebHJp" +
          ".YpygxuHuq0i00vovpazixQvUqu", //123
      "active",
      new HashSet<>(),
      new ArrayList<>()
  );
  
  @Test
  void getAllCustomers() throws Exception {
    this.mockMvc.perform(get("/api/v1/customers"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(
            "[{\"id\":1,\"firstName\":\"Egor\",\"password\":\"Vakulenko\"," +
                "\"userStatus\":\"active\",\"roles\":[{\"id\":1,\"roleName\":\"ADMIN\"}]},"
                +
                "{\"id\":2,\"firstName\":\"Andrew\",\"password\":\"Kos\"," +
                "\"userStatus\":\"active\",\"roles\":[{\"id\":3,\"roleName\":\"CUSTOMER\"}]},"
                +
                "{\"id\":3,\"firstName\":\"Vasya\",\"password\":\"Antonov\"," +
                "\"userStatus\":\"active\",\"roles\":[{\"id\":3,\"roleName\":\"CUSTOMER\"}]}]"));
  }
  
  @Test
  void getCustomer() throws Exception {
    this.mockMvc.perform(get("/api/v1/customers/2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(
            "{\"id\":2,\"firstName\":\"Andrew\",\"password\":\"Kos\"," +
                "\"userStatus\":\"active\",\"roles\":[{\"id\":3,\"roleName\":\"CUSTOMER\"}]}"));
  }
  
  @Test
  void createNewCustomer() throws Exception {
    this.mockMvc.perform(post("/api/v1/customers")
        .with(csrf())
        .content(new ObjectMapper().writeValueAsString(userEntity))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());
    
    //checking that object was created
    this.mockMvc.perform(get("/api/v1/customers/4"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"id\":4,\"firstName\":\"Alex\"," +
            "\"password\":\"$2a$04$0bFPDA4bcQApDiCTJGl0mebHJp.YpygxuHuq0i00vovpazixQvUqu\"," +
            "\"userStatus\":\"active\",\"roles\":[{\"id\":3,\"roleName\":\"CUSTOMER\"}]}"));
  }
  
  @Test
  void deactivateCustomer() throws Exception {
    //deleting object
    this.mockMvc.perform(delete("/api/v1/customers/3")
        .with(csrf()))
        .andDo(print())
        .andExpect(status().isNoContent());
    
    //checking that object was deleted
    this.mockMvc.perform(get("/api/v1/customers"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(
            "[{\"id\":1,\"firstName\":\"Egor\",\"password\":\"Vakulenko\"," +
                "\"userStatus\":\"active\",\"roles\":[{\"id\":1,\"roleName\":\"ADMIN\"}]},"
                +
                "{\"id\":2,\"firstName\":\"Andrew\",\"password\":\"Kos\"," +
                "\"userStatus\":\"active\",\"roles\":[{\"id\":3,\"roleName\":\"CUSTOMER\"}]}]"));
  }
}
