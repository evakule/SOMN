package com.somn.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerAccountDTO extends AccountantAccountDTO {
  private Integer balance;
  
  public CustomerAccountDTO(
      Long id,
      Integer balance,
      String accountStatus,
      Long userId
  ) {
    super(id, accountStatus, userId);
    this.balance = balance;
  }
}
