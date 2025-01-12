package com.nos.home.admin.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdminAccountInfoDto {
    private String userId;
    private String userName;
}
