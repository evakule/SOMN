package com.somn.repository;

import com.somn.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {
}
