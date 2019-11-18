package com.somn.service;

import com.somn.dto.AccountDTO;

import com.somn.model.exception.SomnLimitExceedException;

import java.util.List;

public interface AccountEntityService {
  List<AccountDTO> getAllAccounts();
  
  AccountDTO getById(Long id);
  
  void createAccount(AccountDTO accountDTO);
  
  void updateAccount(AccountDTO accountDTO);
  
  void deleteAccount(Long id);
  
  void withdrawMoneyFromAccount(final Long id, final Integer amount)
      throws SomnLimitExceedException;
  
  void depositMoney(final Long id, final Integer amount)
      throws SomnLimitExceedException;
}
