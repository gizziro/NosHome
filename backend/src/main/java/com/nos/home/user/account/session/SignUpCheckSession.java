package com.nos.home.user.account.session;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SignUpCheckSession {
    private String      uuid;
    private int         sendOtpCount;
    private boolean     otpCheck;
    private String      otpNumber;

    public SignUpCheckSession() {
        this.uuid = UUID.randomUUID().toString();;
        this.sendOtpCount = 0;
        this.otpCheck = false;
    }
}
