package com.somn.mappers;

import com.somn.dto.RoleDTO;
import com.somn.model.RoleEntity;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
  RoleDTO toDTO(RoleEntity roleEntity);
}
