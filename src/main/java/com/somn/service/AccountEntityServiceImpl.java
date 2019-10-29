package com.somn.service;

import com.somn.model.AccountEntity;
import com.somn.model.exception.SomnLimitExceedException;
import com.somn.repository.AccountEntityRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class AccountEntityServiceImpl implements AccountEntityService {
  @Value("${somn.operation.limit}")
  private Integer operationLimit;
  @Value("${somn.balance.max-amount}")
  private Integer balanceLimit;
  
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
  public void withdrawMoneyFromAccount(final Long id, final Integer amount)
      throws SomnLimitExceedException {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    Integer oldBalance = accountEntity.getBalance();
    if (amount > oldBalance || amount < operationLimit) {
      throw new SomnLimitExceedException();
    }
    Integer newBalance = oldBalance - amount;
    accountEntity.setBalance(newBalance);
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void depositMoney(final Long id, final Integer amount)
      throws SomnLimitExceedException {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    Integer oldBalance = accountEntity.getBalance();
    if (amount + oldBalance > balanceLimit || amount < operationLimit) {
      throw new SomnLimitExceedException();
    }
    Integer newBalance = oldBalance + amount;
    accountEntity.setBalance(newBalance);
    accountEntityRepository.save(accountEntity);
  }
}
