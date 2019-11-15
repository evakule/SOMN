package com.somn.mappers;

import com.somn.dto.AccountDTO;
import com.somn.model.AccountEntity;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
  AccountDTO toDTO(AccountEntity accountEntity);
  
  AccountEntity toEntity(AccountDTO accountDTO);
  
  List<AccountDTO> toDtoList(List<AccountEntity> accountEntityList);
}
