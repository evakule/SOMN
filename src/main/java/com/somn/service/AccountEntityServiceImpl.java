package com.somn.service;

import com.somn.model.AccountEntity;
import com.somn.repository.AccountEntityRepository;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountEntityServiceImpl implements AccountEntityService {
  
  @Autowired
  private AccountEntityRepository accountEntityRepository;
  
  @Override
  public Optional<List<AccountEntity>> getAllAccounts() {
    return Optional.ofNullable(accountEntityRepository.findAll());
  }
  
  @Override
  public Optional<AccountEntity> getById(final Long id) {
    return accountEntityRepository.findById(id);
  }
  
  @Override
  public void createAccount(final AccountEntity accountEntity) {
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void updateAccount(final AccountEntity accountEntity) {
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void deleteAccount(final Long id) {
    accountEntityRepository.deleteById(id);
  }
  
  @Override
  public ResponseEntity<AccountEntity> withdrawMoneyFromAccount(
      final Long id,
      final Integer amount,
      final Integer operationLimit,
      final Integer balanceLimit
  ) {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    Integer oldBalance = accountEntity.getBalance();
    if (amount > oldBalance || amount < operationLimit) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      Integer newBalance = oldBalance - amount;
      accountEntity.setBalance(newBalance);
      accountEntityRepository.save(accountEntity);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
  
  @Override
  public ResponseEntity<AccountEntity> replenishAccount(
      final Long id,
      final Integer amount,
      final Integer operationLimit,
      final Integer balanceLimit
  ) {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    Integer oldBalance = accountEntity.getBalance();
    if (amount + oldBalance > balanceLimit || amount < operationLimit) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      Integer newBalance = oldBalance + amount;
      accountEntity.setBalance(newBalance);
      accountEntityRepository.save(accountEntity);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
