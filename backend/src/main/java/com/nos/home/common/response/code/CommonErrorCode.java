package com.nos.home.common.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonErrorCode implements IErrorCode
{
    OK                  ("0000" , "해당 요청을 처리하였습니다.", null),
    BAD_REQUEST         ("4000" , "잘못된 요청", null),
    NOT_FOUND           ("4004" , "해당 요청을 처리할 수 없습니다.", null),
    NOT_LOGINED         ("4005" , "해당 요청을 처리할 수 없습니다.","서비스 이용을 위해 로그인을 하시기 바랍니다."),
    NOT_AUTHENTICATED   ("4006" , "해당 요청을 처리할 수 없습니다.", null),
    INVALID_STATE       ("4007" , "올바르지 않은 요청 상태입니다.", null),
    NOT_ACCEPTED        ("5001" , "일치하지 않습니다.", null);

    private final String    code;
    private final String    message;
    private final String    description;
}
