package com.somn.mappers;

import com.somn.dto.UserDTO;
import com.somn.model.UserEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
  @Mapping(source = "encryptedPassword", target = "password")
  UserDTO toDTO(UserEntity userEntity);
  
  @Mapping(source = "password", target = "encryptedPassword")
  UserEntity toEntity(UserDTO userDTO);
  
  List<UserDTO> toDtoList(List<UserEntity> userEntityList);
}