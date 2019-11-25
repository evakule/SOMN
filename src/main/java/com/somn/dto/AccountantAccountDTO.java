package com.somn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountantAccountDTO {
  private Long id;
  private String accountStatus;
  private Long userId;
}
