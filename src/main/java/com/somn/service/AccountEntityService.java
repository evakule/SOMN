package com.somn.service;

import com.somn.model.AccountEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public interface AccountEntityService {
  Optional<List<AccountEntity>> getAllAccounts();
  
  Optional<AccountEntity> getById(Long id);
  
  void createAccount(AccountEntity accountEntity);
  
  void updateAccount(AccountEntity accountEntity);
  
  void deleteAccount(Long id);
  
  void withdrawMoneyFromAccount(final Long id, final Integer amount);
  
  void depositMoney(final Long id, final Integer amount);
}
