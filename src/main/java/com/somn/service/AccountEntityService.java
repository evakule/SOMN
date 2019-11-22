package com.somn.service;

import com.somn.dto.AccountDTO;

import com.somn.exception.SomnLimitExceedException;

import java.util.List;

public interface AccountEntityService {
  List<AccountDTO> getAllAccounts();
  
  List<AccountDTO> getAllAccountsWithoutBalance();
  
  AccountDTO getById(Long id);
  
  AccountDTO getAccountByIdWithoutBalance(final Long id);
  
  void createAccount(AccountDTO accountDTO);
  
  void deleteAccount(Long id);
  
  List<AccountDTO> getAllCustomerAccountsById(Long id);
  
  void withdrawMoneyFromAccount(final Long id, final Integer amount)
      throws SomnLimitExceedException;
  
  void depositMoney(final Long id, final Integer amount)
      throws SomnLimitExceedException;
}
