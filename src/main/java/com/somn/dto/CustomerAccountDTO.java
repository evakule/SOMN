package com.somn.dto;

import com.somn.model.status.AccountStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
    value = "Account", description = "Represents an account")
@Data
@NoArgsConstructor
public class CustomerAccountDTO extends AccountantAccountDTO {
  @ApiModelProperty(
      notes = "Account balance of the customer.",
      example = "500000", required = true,
      position = 1)
  private Integer balance;
  
  public CustomerAccountDTO(
      Long id,
      Integer balance,
      AccountStatus accountStatus,
      Long userId
  ) {
    super(id, accountStatus, userId);
    this.balance = balance;
  }
}
