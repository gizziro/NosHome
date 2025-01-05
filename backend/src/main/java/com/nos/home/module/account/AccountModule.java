package com.nos.home.module.account;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.ModulePermission;
import org.springframework.stereotype.Component;

@Component
public class AccountModule extends BaseModule {
    public ModuleType moduleType = ModuleType.SYSTEM;

    @Override
    public String getModuleId() {
        return "ACCOUNT";
    }

    @Override
    public String getModuleName() {
        return "회원 관리";
    }

    @Override
    public String getDescription() {
        return "시스템 회원 및 그룹 관리 기능";
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public Class<? extends ModulePermission> getPermissionEnum() {
        return AccountModulePermission.class;
    }
}