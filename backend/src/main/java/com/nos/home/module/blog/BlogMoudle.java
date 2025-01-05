package com.nos.home.module.blog;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.ModulePermission;
import org.springframework.stereotype.Component;

@Component
public class BlogMoudle extends BaseModule {
    public ModuleType moduleType = ModuleType.SERVICE;

    @Override
    public String getModuleId() {
        return "BLOG";
    }

    @Override
    public String getModuleName() {
        return "블로그";
    }

    @Override
    public String getDescription() {
        return "블로그 포스팅 및 관리 모듈";
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public Class<? extends ModulePermission> getPermissionEnum() {
        return BlogModulePermission.class;
    }
}
