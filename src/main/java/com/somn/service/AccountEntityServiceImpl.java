package com.somn.service;

import com.somn.dto.AccountantAccountDTO;
import com.somn.dto.CustomerAccountDTO;
import com.somn.mappers.AccountantAccountMapper;
import com.somn.mappers.CustomerAccountMapper;
import com.somn.model.AccountEntity;
import com.somn.model.status.AccountStatus;
import com.somn.repository.AccountEntityRepository;
import com.somn.repository.UserEntityRepository;

import com.somn.service.exception.DeactivatedAccountException;
import com.somn.service.exception.NoSuchUserException;
import com.somn.service.exception.SomnLimitExceedException;
import com.somn.service.exception.UnableActivateAccountException;
import com.somn.service.exception.UnableDeactivateAccountException;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
  private String balanceStoreLimitMessage;
  @Value("${somn.user.no-such-user-message}")
  private String noSuchUserMessage;
  @Value("${somn.account.unable-activate-account-message}")
  private String unableActivateAccountMessage;
  @Value("${somn.account.deactivated-account-message}")
  private String deactivatedAccountMessage;
  @Value("${somn.account.unable-deactivate-account-message}")
  private String unableDeactivateAccountMessage;
  
  
  @Autowired
  private AccountEntityRepository accountEntityRepository;
  
  @Autowired
  private AccountantAccountMapper accountantAccountMapper;
  
  @Autowired
  private CustomerAccountMapper customerAccountMapper;
  
  @Autowired
  private UserEntityRepository userEntityRepository;
  
  @Override
  public List<AccountantAccountDTO> getAllAccounts() {
    List<AccountEntity> accountEntityList = accountEntityRepository.findAll();
    log.debug("{} Accounts found", accountEntityList.size());
    return accountantAccountMapper.toDtoList(accountEntityList);
  }
  
  @Override
  public AccountantAccountDTO getById(final Long id) {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    log.debug("Account of user - '{}', found",
        accountEntity.getUserEntity().getFirstName());
    return accountantAccountMapper.toDTO(accountEntity);
  }
  
  @Override
  public void createAccount(final AccountantAccountDTO accountantAccountDTO)
      throws NoSuchUserException {
    Long userId = accountantAccountDTO.getUserId();
    if (!userEntityRepository.existsById(userId)) {
      throw new NoSuchUserException(noSuchUserMessage);
    }
    AccountEntity accountEntity =
        accountantAccountMapper.toEntity(accountantAccountDTO);
    accountEntity.setBalance(0);
    log.debug(
        "New account of user - '{}', was created",
        getFirstUserNameByAccount(accountEntity));
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public List<CustomerAccountDTO> getAllCustomerAccountsById(final Long id) {
    List<AccountEntity> accountEntityList =
        accountEntityRepository.getAllByUserEntityId(id);
    log.debug(
        "{} accounts of user - {}, was found",
        accountEntityList.size(),
        getFirstUserNameByAccount(accountEntityList.get(0)));
    return customerAccountMapper.toDtoList(accountEntityList);
  }
  
  @Override
  public void deactivateAccount(final Long id)
      throws UnableDeactivateAccountException {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    if (accountEntity.getAccountStatus().equals(AccountStatus.DEACTIVATED)) {
      throw new UnableDeactivateAccountException(unableDeactivateAccountMessage);
    }
    accountEntity.setAccountStatus(AccountStatus.DEACTIVATED);
    log.debug(
        "Account of user {}, with id - '{}', deactivated",
        getFirstUserNameByAccount(accountEntity),
        accountEntity.getId());
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void activateAccount(final Long id)
      throws UnableActivateAccountException {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    if (accountEntity.getAccountStatus().equals(AccountStatus.ACTIVE)) {
      throw new UnableActivateAccountException(unableActivateAccountMessage);
    }
    accountEntity.setAccountStatus(AccountStatus.ACTIVE);
    log.debug(
        "Account of user {}, with id - '{}', activated",
        getFirstUserNameByAccount(accountEntity),
        accountEntity.getId());
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void withdrawMoneyFromAccount(final Long id, final Integer amount)
      throws SomnLimitExceedException, DeactivatedAccountException {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    Integer oldBalance = accountEntity.getBalance();
    if (amount < operationLimit) {
      throw new SomnLimitExceedException(operationValueExceptionMessage);
    }
    if (amount > oldBalance) {
      throw new SomnLimitExceedException(balanceWithdrawExceptionMessage);
    }
    if (accountEntity.getAccountStatus().equals(AccountStatus.DEACTIVATED)) {
      throw new DeactivatedAccountException(deactivatedAccountMessage);
    }
    Integer newBalance = oldBalance - amount;
    accountEntity.setBalance(newBalance);
    log.debug(
        "Money of user - {}, was withdraw from the account "
            + "with id - '{}'",
        getFirstUserNameByAccount(accountEntity),
        accountEntity.getId());
    accountEntityRepository.save(accountEntity);
  }
  
  @Override
  public void depositMoney(final Long id, final Integer amount)
      throws SomnLimitExceedException, DeactivatedAccountException {
    AccountEntity accountEntity = accountEntityRepository.getOne(id);
    Integer oldBalance = accountEntity.getBalance();
    if (amount < operationLimit) {
      throw new SomnLimitExceedException(operationValueExceptionMessage);
    }
    if (amount + oldBalance > balanceLimit) {
      throw new SomnLimitExceedException(balanceStoreLimitMessage);
    }
    if (accountEntity.getAccountStatus().equals(AccountStatus.DEACTIVATED)) {
      throw new DeactivatedAccountException(deactivatedAccountMessage);
    }
    Integer newBalance = oldBalance + amount;
    accountEntity.setBalance(newBalance);
    log.debug(
        "Money of user - {}, was deposited to the account "
            + "with id - '{}'",
        getFirstUserNameByAccount(accountEntity),
        accountEntity.getId());
    accountEntityRepository.save(accountEntity);
  }
  
  private String getFirstUserNameByAccount(AccountEntity accountEntity) {
    return userEntityRepository.getOne(
        accountEntity
            .getUserEntity()
            .getId())
        .getFirstName();
  }
}
