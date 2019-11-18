package com.somn.mappers;

import com.somn.dto.UserDTO;
import com.somn.model.UserEntity;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
  UserDTO toDTO(UserEntity userEntity);
  
  UserEntity toEntity(UserDTO userDTO);
  
  List<UserDTO> toDtoList(List<UserEntity> userEntityList);
}