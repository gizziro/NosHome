package com.nos.home.repository.account;

import com.nos.home.entity.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUserId(String username);
    AccountEntity findByEmail(String email);
}
