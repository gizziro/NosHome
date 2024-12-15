package com.nos.home.common.security.details;

import com.nos.home.entity.account.AccountEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@Slf4j
public class AccountDto {
    private Long            userSeq;
    private String          userId;
    private String          name;
    private String          password;
    private String          email;              // 사용자 이메일
    private boolean         emailVerified;      // 이메일 인증 여부
    private String          emailToken;         // 이메일 토큰 (회원 가입 시 이메일 인증 토큰)


    private String      phone;

    private boolean     enabled;

    //------------------------------------------------------------------------------------------------------------------
    // [로그인 실패 및 그에 따른 잠금 처리 여부]
    //------------------------------------------------------------------------------------------------------------------
    private int                 failedLoginAttempts;        // 로그인 실패 횟수
    private boolean             locked;                     // 계정 잠금 여부
    private LocalDateTime       lockedDate;                 // 계정 잠긴 날짜

    //------------------------------------------------------------------------------------------------------------------
    // [SMS MFA 관련 정보]
    //------------------------------------------------------------------------------------------------------------------
    private boolean             useMfa;                     // MFA 사용 여부
    private boolean             mfaOtpVerified;             // MFA OTP 인증 여부
    private String              mfaOtpToken;                // MFA OTP 토큰
    private String              mfaOtpNumber;               // MFA SMS OTP 번호
    private LocalDateTime       mfaLastRequestDate;         // 마지막 MFA 요청 시간
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // [관리자 OTP 수행 여부]
    //------------------------------------------------------------------------------------------------------------------
    private boolean             adminOtpVerified;           // OTP 인증 여부
    private String              adminOtpToken;              // OTP 토큰
    private String              adminOtpNumber;             // SMS OTP 번호
    private LocalDateTime       adminOtpVerifiedDate;       // OTP 인증 날짜
    private LocalDateTime       adminLastRequestDate;       // 마지막 관리자 요청 시간
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // DB Entity -> DTO 변환
    //------------------------------------------------------------------------------------------------------------------
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

        //--------------------------------------------------------------------------------------------------------------
        // SMS OTP 관련 정보
        //--------------------------------------------------------------------------------------------------------------
        accountDto.adminOtpVerified          = false;
        return accountDto;
    }

    private String GernerateOtpNumber()
    {
        Random random          = new Random();
        int     sixDigitNumber  = 100000 + random.nextInt(900000);
        return String.valueOf(sixDigitNumber);
    }

    public boolean GenerateMfaOtpNumber()
    {
        //--------------------------------------------------------------------------------------------------------------
        // 현재 시간을 가져온다.
        //--------------------------------------------------------------------------------------------------------------
        LocalDateTime now = LocalDateTime.now();
        //--------------------------------------------------------------------------------------------------------------
        // 최초 요청이거나, 마지막 요청으로부터 3분이 지났으면 새로운 OTP 생성
        //--------------------------------------------------------------------------------------------------------------
        if (getMfaLastRequestDate() == null || now.isAfter(getMfaLastRequestDate().plusMinutes(3)))
        {
            this.mfaOtpNumber = GernerateOtpNumber();
            setMfaLastRequestDate(now);
            log.info("새로운 OTP 생성: {}", getMfaOtpNumber());
            return true;
        }
        else
        {
            log.info("기존 OTP 사용: {}", getMfaOtpNumber());
            return false;
        }
    }

    public boolean GenerateAdminOtpNumber()
    {
        //--------------------------------------------------------------------------------------------------------------
        // 현재 시간을 가져온다.
        //--------------------------------------------------------------------------------------------------------------
        LocalDateTime now = LocalDateTime.now();
        //--------------------------------------------------------------------------------------------------------------
        // 최초 요청이거나, 마지막 요청으로부터 3분이 지났으면 새로운 OTP 생성
        //--------------------------------------------------------------------------------------------------------------
        if (getMfaLastRequestDate() == null || now.isAfter(getMfaLastRequestDate().plusMinutes(3)))
        {
            this.adminOtpNumber = GernerateOtpNumber();
            setMfaLastRequestDate(now);
            log.info("새로운 관리자 OTP 생성: {}", getAdminOtpNumber());
            return true;
        }
        else
        {
            log.info("기존 관리자 OTP 사용: {}", getAdminOtpNumber());
            return false;
        }
    }
}

