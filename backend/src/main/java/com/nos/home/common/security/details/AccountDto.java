package com.nos.home.common.security.details;

import com.nos.home.entity.account.AccountEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountDto {
    private Long        userSeq;
    private String          userId;
    private String          name;
    private String          password;
    private String          email;              // 사용자 이메일
    private boolean         emailVerified;      // 이메일 인증 여부
    private String          emailToken;         // 이메일 토큰 (회원 가입 시 이메일 인증 토큰)


    private String      phone;
    private boolean     useMfa;
    private boolean     enabled;

    //------------------------------------------------------------------------------------------------------------------
    // [로그인 실패 및 그에 따른 잠금 처리 여부]
    //------------------------------------------------------------------------------------------------------------------
    private int             failedLoginAttempts;
    private boolean         locked;
    private LocalDateTime   lockedDate;


    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public static AccountDto of(AccountEntity entity)
    {
        AccountDto accountDto           = new AccountDto();
        accountDto.userId               = entity.getUserId();
        accountDto.password             = entity.getPassword();
        accountDto.email                = entity.getEmail();
        accountDto.name                 = entity.getName();
        accountDto.phone                = entity.getPhone();
        accountDto.useMfa               = entity.isUseMfa();
        accountDto.enabled              = entity.isEnabled();
        accountDto.failedLoginAttempts  = entity.getFailedLoginAttempts();
        accountDto.locked               = entity.isLocked();
        accountDto.lockedDate           = entity.getLockedDate();
        return accountDto;
    }
}

