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
  
  ResponseEntity<AccountEntity> withdrawMoneyFromAccount(
      final Long id,
      final Integer amount,
      final Integer operationLimit,
      final Integer balanceLimit
  );
  
  ResponseEntity<AccountEntity> replenishAccount(
      final Long id,
      final Integer amount,
      final Integer operationLimit,
      final Integer balanceLimit
  );
}
