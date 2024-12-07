package com.nos.home.common.security.details;

import com.nos.home.entity.account.AccountEntity;
import lombok.Getter;

@Getter
public class AccountDto {
    private Long        id;
    private String      username;
    private String      password;
    private String      email;
    private String      name;

    public static AccountDto of(AccountEntity entity)
    {
        AccountDto accountDto   = new AccountDto();
        accountDto.id           = entity.getId();
        accountDto.username     = entity.getUsername();
        accountDto.password     = entity.getPassword();
        accountDto.email        = entity.getEmail();
        accountDto.name         = entity.getName();
        return accountDto;
    }
}

