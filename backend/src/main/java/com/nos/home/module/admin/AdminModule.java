package com.nos.home.module.admin;


import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.ModulePermission;
import org.springframework.stereotype.Component;

@Component
public class AdminModule extends BaseModule {
    public ModuleType moduleType = ModuleType.ADMIN;

    @Override
    public String getModuleId() {
        return "ADMIN";
    }

    @Override
    public String getModuleName() {
        return "관리자 페이지";
    }

    @Override
    public String getDescription() {
        return "관리자 페이지 접근 및 시스템 관리 기능";
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public Class<? extends ModulePermission> getPermissionEnum() {
        return AdminModulePermission.class;
    }
}