package com.somn.dto;

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(
    value = "User",
    description = "Represents a user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  @ApiModelProperty(
      notes = "Unique identifier of the user.",
      example = "1",
      position = 0)
  private Long id;
  
  @ApiModelProperty(
      notes = "First name of the user.",
      example = "Olga",
      position = 1)
  @NotNull
  private String firstName;
  
  @ApiModelProperty(
      notes = "Encrypted password of the user.",
      example = "^*&^&*^*(",
      position = 2)
  @NotNull
  private String encryptedPassword;
  
  @ApiModelProperty(
      notes = "User status.",
      example = "inactive",
      position = 3)
  @NotNull
  private String userStatus;
  
  @ApiModelProperty(
      notes = "Set of roles which give some authorities to a user",
      example = "ROLE_ADMIN",
      position = 4)
  private Set<RoleDTO> roles;
}
