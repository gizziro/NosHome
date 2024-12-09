package com.nos.home.repository.account;

import com.nos.home.entity.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUserId(String username);
    Optional<AccountEntity> findByEmail(String email);
}
