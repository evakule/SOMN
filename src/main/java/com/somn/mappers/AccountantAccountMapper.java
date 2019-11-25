package com.somn.mappers;

import com.somn.dto.AccountantAccountDTO;
import com.somn.model.AccountEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountantAccountMapper {
  @Mapping(source = "id", target = "id")
  @Mapping(source = "accountStatus", target = "accountStatus")
  @Mapping(source = "userEntity.id", target = "userId")
  AccountantAccountDTO toDTO(AccountEntity accountEntity);
  
  @Mapping(source = "id", target = "id")
  @Mapping(source = "accountStatus", target = "accountStatus")
  @Mapping(source = "userId", target = "userEntity.id")
  AccountEntity toEntity(AccountantAccountDTO accountantAccountDTO);
  
  List<AccountantAccountDTO> toDtoList(List<AccountEntity> accountEntityList);
}
