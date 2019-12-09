package com.somn.dto;

import com.somn.model.status.AccountStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
  @Enumerated(EnumType.STRING)
  private AccountStatus accountStatus;
  
  @ApiModelProperty(
      notes = "Account holder id.",
      example = "3",
      position = 4)
  @NotNull
  private Long userId;
}
