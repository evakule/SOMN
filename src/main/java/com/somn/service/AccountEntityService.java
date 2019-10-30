package com.somn.service;

import com.somn.model.AccountEntity;

import com.somn.model.exception.SomnLimitExceedException;

import java.util.List;
import java.util.Optional;

public interface AccountEntityService {
  Optional<List<AccountEntity>> getAllAccounts();
  
  Optional<AccountEntity> getById(Long id);
  
  void createAccount(AccountEntity accountEntity);
  
  void updateAccount(AccountEntity accountEntity);
  
  void deleteAccount(Long id);
  
  void withdrawMoneyFromAccount(final Long id, final Integer amount)
      throws SomnLimitExceedException;
  
  void depositMoney(final Long id, final Integer amount)
      throws SomnLimitExceedException;
}
