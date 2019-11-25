package com.somn.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Long id;
  private String firstName;
  private String password;
  private String userStatus;
  private Set<RoleDTO> roles;
}
