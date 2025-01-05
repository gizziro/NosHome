package com.nos.home.view.user.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpOtpRespDto {
    public String transactionId;

    public SignUpOtpRespDto(String transactionId) {
        this.transactionId = transactionId;
    }
}
