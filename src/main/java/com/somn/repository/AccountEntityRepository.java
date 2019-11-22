package com.somn.repository;

import com.somn.model.AccountEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
  List<AccountEntity> getAllByUserEntityId(Long id);
}
