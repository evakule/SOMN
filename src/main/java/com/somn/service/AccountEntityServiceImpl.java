package com.somn.service;

import com.somn.model.AccountEntity;
import com.somn.repository.AccountEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountEntityServiceImpl implements AccountEntityService {
  
  @Autowired
  private AccountEntityRepository accountEntityRepository;
  
  @Override
  public Optional<List<AccountEntity>> getAllAccounts() {
    return Optional.ofNullable(accountEntityRepository.findAll());
  }
  
  @Override
  public Optional<AccountEntity> getById(Long id) {
    return accountEntityRepository.findById(id);
  }
  
  @Override
  public void createAccount(AccountEntity accountEntity) {
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void updateAccount(AccountEntity accountEntity) {
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void deleteAccount(Long id) {
    accountEntityRepository.deleteById(id);
  }
}
