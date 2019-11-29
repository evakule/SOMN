package com.somn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
    value = "Account", description = "Represents an account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountantAccountDTO {
  @ApiModelProperty(
      notes = "Unique identifier of the account.",
      example = "1",
      position = 0)
  private Long id;
  
  @ApiModelProperty(
      notes = "Account status.",
      example = "active",
      position = 2)
  @NotNull
  private String accountStatus;
  
  @ApiModelProperty(
      notes = "Account holder id.",
      example = "3",
      position = 4)
  @NotNull
  private Long userId;
}
