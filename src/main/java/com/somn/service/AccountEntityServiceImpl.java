package com.somn.service;

import com.somn.dto.AccountantAccountDTO;
import com.somn.dto.CustomerAccountDTO;
import com.somn.exception.SomnLimitExceedException;
import com.somn.mappers.AccountantAccountMapper;
import com.somn.mappers.CustomerAccountMapper;
import com.somn.model.AccountEntity;
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
  private AccountantAccountMapper accountantAccountMapper;
  
  @Autowired
  private CustomerAccountMapper customerAccountMapper;
  
  @Override
  public List<AccountantAccountDTO> getAllAccounts() {
    List<AccountEntity> accountEntityList = accountEntityRepository.findAll();
    return accountantAccountMapper.toDtoList(accountEntityList);
  }
  
  @Override
  public AccountantAccountDTO getById(final Long id) {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    return accountantAccountMapper.toDTO(accountEntity);
  }
  
  @Override
  public void createAccount(final CustomerAccountDTO customerAccountDTO)
      throws SomnLimitExceedException {
    if (customerAccountDTO.getBalance() > balanceLimit) {
      throw new SomnLimitExceedException(balanceStoreLimitException);
    }
    AccountEntity accountEntity = customerAccountMapper.toEntity(customerAccountDTO);
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public List<CustomerAccountDTO> getAllCustomerAccountsById(Long id) {
    List<AccountEntity> accountEntityList = accountEntityRepository.getAllByUserEntityId(id);
    return customerAccountMapper.toDtoList(accountEntityList);
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
