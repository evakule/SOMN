package com.somn.mappers;

import com.somn.dto.AccountDTO;
import com.somn.model.AccountEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {
  @Mapping(source = "userEntity.id", target = "userId")
  AccountDTO toDTO(AccountEntity accountEntity);
  
  @Mapping(source = "userId", target = "userEntity.id")
  AccountEntity toEntity(AccountDTO accountDTO);
  
  List<AccountDTO> toDtoList(List<AccountEntity> accountEntityList);
  
  default List<AccountDTO> toDtoListWithoutBalance(List<AccountEntity> accountEntityList) {
    return accountEntityList
        .stream()
        .map(a -> new AccountDTO(
            a.getId(),
            a.getAccountStatus(),
            a.getUserEntity().getId()))
        .collect(Collectors.toList());
  }
}
