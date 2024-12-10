package com.nos.home.common.security.provider;

import com.nos.home.common.security.details.AccountContext;
import com.nos.home.common.security.details.AccountDto;
import com.nos.home.repository.account.AccountRepository;
import com.nos.home.entity.account.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class NosUserDetailsService  implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String target) throws UsernameNotFoundException {
        //--------------------------------------------------------------------------------------------------------------
        // 사용자 정보를 DB에서 조회한다.
        //--------------------------------------------------------------------------------------------------------------
        AccountEntity accountEntity = accountRepository.findByUserId(target);

        //--------------------------------------------------------------------------------------------------------------
        // 사용자가 존재하지 않으면, 이메일을 이용하여 검색하고, 없다면 예외를 발생한다.
        //--------------------------------------------------------------------------------------------------------------
        if(accountEntity == null) {
            accountEntity = accountRepository.findByEmail(target);
            if(accountEntity == null) {
                throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다. : " + target);
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        //
        //--------------------------------------------------------------------------------------------------------------
        AccountDto      accountDto      = AccountDto.of(accountEntity);

        return new AccountContext(accountDto, null);
    }
}
