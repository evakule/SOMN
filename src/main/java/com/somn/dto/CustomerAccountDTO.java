package com.somn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountDTO {
  private Long id;
  private Integer balance;
  private String accountStatus;
  private Long userId;
}
