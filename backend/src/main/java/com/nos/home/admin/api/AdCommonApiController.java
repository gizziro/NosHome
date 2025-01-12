package com.nos.home.admin.api;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.dto.ModuleTypeDto;
import com.nos.home.common.response.ApiResponse;
import com.nos.home.common.security.CurrentUser;
import com.nos.home.common.security.details.AccountDto;
import com.nos.home.common.util.ModuleHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/common/")
@RequiredArgsConstructor
public class AdCommonApiController {
    private final ModuleHelper moduleHelper;



    @GetMapping("moduleList")
    public ApiResponse<List<ModuleTypeDto>> moduleList(@CurrentUser AccountDto account)
    {
        List<ModuleTypeDto> modules = moduleHelper.getModuleDtos(BaseModule.ModuleType.SERVICE);
        return ApiResponse.OK(modules);
    }

}
