package com.nos.home.common.security.details;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class AccountContext implements UserDetails {

    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    public final AccountDto account;

    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private List<GrantedAuthority>  authorities;

    public AccountContext(AccountDto user) {
        this.account = user;
    }

    public AccountContext(AccountDto accountDto, List<GrantedAuthority> authorities) {
        this.account = accountDto;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getName();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account.isLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 또는 실제 계정 만료 여부에 따른 로직
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AccountDto getAccount() {  // Getter 추가
        return this.account;
    }
}