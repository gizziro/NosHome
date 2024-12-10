package com.nos.home.service.account;

import com.nos.home.entity.account.AccountEntity;
import com.nos.home.repository.account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private final AccountRepository accountRepository;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 ID 중복 체크
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public boolean checkId(String userId) {
        AccountEntity userEntity = accountRepository.findByUserId(userId);
        return (userEntity != null);
    }

}
