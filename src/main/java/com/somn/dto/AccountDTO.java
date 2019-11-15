package com.somn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
  private Long id;
  private Integer balance;
  private String accountStatus;
  
  public AccountDTO(Long id, String accountStatus) {
    this.id = id;
    this.accountStatus = accountStatus;
  }
}
