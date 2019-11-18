package com.somn.service;

import com.somn.dto.AccountDTO;
import com.somn.mappers.AccountMapper;
import com.somn.model.AccountEntity;
import com.somn.model.exception.SomnLimitExceedException;
import com.somn.repository.AccountEntityRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class AccountEntityServiceImpl implements AccountEntityService {
  @Value("${somn.operation.limit}")
  private Integer operationLimit;
  @Value("${somn.balance.max-amount}")
  private Integer balanceLimit;
  @Value("${somn.operation.value-exception-message}")
  private String operationValueExceptionMessage;
  @Value("${somn.balance.withdraw-exception-message}")
  private String balanceWithdrawExceptionMessage;
  @Value("${somn.balance.store-limit-exception-message}")
  private String balanceStoreLimitException;
  
  @Autowired
  private AccountEntityRepository accountEntityRepository;
  
  @Autowired
  private AccountMapper accountMapper;
  
  @Override
  public List<AccountDTO> getAllAccounts() {
    List<AccountEntity> accountEntityList = accountEntityRepository.findAll();
    return accountMapper.toDtoList(accountEntityList);
  }
  
  @Override
  public AccountDTO getById(final Long id) {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    return accountMapper.toDTO(accountEntity);
  }
  
  @Override
  public void createAccount(final AccountDTO accountDTO) {
    AccountEntity accountEntity = accountMapper.toEntity(accountDTO);
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void updateAccount(final AccountDTO accountDTO) {
    AccountEntity accountEntity = accountMapper.toEntity(accountDTO);
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
    if (amount < operationLimit) {
      throw new SomnLimitExceedException(operationValueExceptionMessage);
    }
    if (amount > oldBalance) {
      throw new SomnLimitExceedException(balanceWithdrawExceptionMessage);
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
    if (amount < operationLimit) {
      throw new SomnLimitExceedException(operationValueExceptionMessage);
    }
    if (amount + oldBalance > balanceLimit) {
      throw new SomnLimitExceedException(balanceStoreLimitException);
    }
    Integer newBalance = oldBalance + amount;
    accountEntity.setBalance(newBalance);
    accountEntityRepository.save(accountEntity);
  }
}
