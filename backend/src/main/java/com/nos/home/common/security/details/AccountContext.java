package com.nos.home.common.security.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class AccountContext implements UserDetails {

    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private AccountDto              accountDto;
    private List<GrantedAuthority>  authorities;

    public AccountContext(AccountDto accountDto, List<GrantedAuthority> authorities) {
        this.accountDto = accountDto;
        this.authorities = authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return accountDto.getPassword();
    }

    @Override
    public String getUsername() {
        return accountDto.getName();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountDto.isLocked();
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}