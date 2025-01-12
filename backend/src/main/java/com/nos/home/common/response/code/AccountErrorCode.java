package com.nos.home.common.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountErrorCode implements IErrorCode
{
    ERROR_401_AUTHENTICATION_REQUIRED  ("401", "USR0001" , "Unauthorized", "해당 페이지를 접근하기 위해서는 로그인이 필요합니다."),
    ERROR_403_FORBIDDEN_FOR_ADMIN  ("403", "USR0002" , "Forbidden", "접근 권한이 없습니다.");


    private final String    status;
    private final String    code;
    private final String    message;
    private final String    description;
}
