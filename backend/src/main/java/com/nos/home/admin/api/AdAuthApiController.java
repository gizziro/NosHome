package com.nos.home.admin.api;

import com.nos.home.admin.dto.account.AdminAccountInfoDto;
import com.nos.home.common.response.ApiResponse;
import com.nos.home.common.response.code.AccountErrorCode;
import com.nos.home.common.security.CurrentUser;
import com.nos.home.common.security.details.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/auth/")
@RequiredArgsConstructor
public class AdAuthApiController {

    @GetMapping("myPrivilege")
    public ApiResponse<AdminAccountInfoDto> myPrivilege(@CurrentUser AccountDto account)
    {
        AdminAccountInfoDto returnDto = null;
        //--------------------------------------------------------------------------------------------------------------
        // 사용자 로그인 체크
        //--------------------------------------------------------------------------------------------------------------
        if(account == null)
        {
            return ApiResponse.ERROR(AccountErrorCode.ERROR_401_AUTHENTICATION_REQUIRED);
        }

        //--------------------------------------------------------------------------------------------------------------
        // 사용자 권한 체크
        //--------------------------------------------------------------------------------------------------------------
//        if(!account.isAdmin())
//        {
//            return ApiResponse.ERROR(AccountErrorCode.ERROR_403_FORBIDDEN_FOR_ADMIN);
//        }

        returnDto = AdminAccountInfoDto.builder()
                .userId(account.getUserId())
                .userName(account.getName())
                .build();

        return ApiResponse.OK(returnDto);
    }
}
