package com.somn.service;

import com.somn.dto.AccountantAccountDTO;
import com.somn.dto.CustomerAccountDTO;

import com.somn.service.exception.NoSuchUserException;
import com.somn.service.exception.SomnLimitExceedException;
import com.somn.service.exception.UnableActivateAccountException;

import java.util.List;

public interface AccountEntityService {
  List<AccountantAccountDTO> getAllAccounts();
  
  AccountantAccountDTO getById(Long id);
  
  void createAccount(AccountantAccountDTO accountantAccountDTO)
      throws NoSuchUserException;
  
  void deactivateAccount(Long id);
  
  void activateAccount(Long id)
      throws UnableActivateAccountException;
  
  List<CustomerAccountDTO> getAllCustomerAccountsById(Long id);
  
  void withdrawMoneyFromAccount(final Long id, final Integer amount)
      throws SomnLimitExceedException;
  
  void depositMoney(final Long id, final Integer amount)
      throws SomnLimitExceedException;
}
