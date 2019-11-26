package com.somn.mappers;

import com.somn.dto.CustomerAccountDTO;
import com.somn.model.AccountEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerAccountMapper {
  @Mapping(source = "userEntity.id", target = "userId")
  CustomerAccountDTO toDTO(AccountEntity accountEntity);
  
  @Mapping(source = "userId", target = "userEntity.id")
  AccountEntity toEntity(CustomerAccountDTO customerAccountDTO);
  
  List<CustomerAccountDTO> toDtoList(List<AccountEntity> accountEntityList);
}
