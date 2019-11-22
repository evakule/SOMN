package com.somn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
  private Long id;
  private Integer balance;
  private String accountStatus;
  private Long userId;
  
  public AccountDTO(Long id, String accountStatus, Long userId) {
    this.id = id;
    this.accountStatus = accountStatus;
    this.userId = userId;
  }
}
