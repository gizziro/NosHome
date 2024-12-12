package com.nos.home.service.account;

import com.nos.home.entity.account.AccountEntity;
import com.nos.home.repository.account.AccountRepository;
import com.nos.home.user.account.dto.SignUpFormDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private final AccountRepository     accountRepository;
    private final PasswordEncoder       passwordEncoder;


    //------------------------------------------------------------------------------------------------------------------
    // 사용자 ID 중복 체크
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public boolean checkId(String userId) {
        AccountEntity userEntity = accountRepository.findByUserId(userId);
        return (userEntity != null);
    }

    @Transactional
    public boolean createUser(SignUpFormDto createDto){
        //--------------------------------------------------------------------------------------------------------------
        // 비밀번호 단방향 암호촤 수행
        //--------------------------------------------------------------------------------------------------------------
        createDto.setPassword(passwordEncoder.encode(createDto.getPassword()));
        AccountEntity entity = AccountEntity.of(createDto);
        accountRepository.save(entity);
        return true;
    }

}
