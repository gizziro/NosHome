package com.nos.home.common.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonErrorCode implements IErrorCode
{
    OK                  ("200", "0000" , "해당 요청을 처리하였습니다.", null),
    BAD_REQUEST         ("200","4000" , "잘못된 요청", null),
    NOT_FOUND           ("200","4004" , "해당 요청을 처리할 수 없습니다.", null),
    NOT_LOGINED         ("200","4005" , "해당 요청을 처리할 수 없습니다.","서비스 이용을 위해 로그인을 하시기 바랍니다."),
    NOT_AUTHENTICATED   ("200","4006" , "해당 요청을 처리할 수 없습니다.", null),
    INVALID_STATE       ("200","4007" , "올바르지 않은 요청 상태입니다.", null),

    ACCOUNT_ALREADY_EXIST   ("200","5002" , "이미 존재하는 사용자 ID 입니다.", null),
    NOT_ACCEPTED            ("200","5001" , "일치하지 않습니다.", null);

    private final String    status;
    private final String    code;
    private final String    message;
    private final String    description;
}
