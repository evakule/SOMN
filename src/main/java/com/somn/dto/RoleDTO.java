package com.somn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
    value = "Role",
    description = "Represents an a role "
        + "which give some authorities to a user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
  @ApiModelProperty(
      notes = "Unique identifier of the role.",
      example = "1",
      position = 0)
  private Long id;
  
  @ApiModelProperty(
      notes = "Role name.",
      example = "ROLE_CUSTOMER",
      position = 1)
  private String roleName;
}
