package com.somn.mappers;

import com.somn.dto.AccountantAccountDTO;
import com.somn.model.AccountEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountantAccountMapper {
  @Mapping(source = "userEntity.id", target = "userId")
  AccountantAccountDTO toDTO(AccountEntity accountEntity);
  
  List<AccountantAccountDTO> toDtoList(List<AccountEntity> accountEntityList);
}
