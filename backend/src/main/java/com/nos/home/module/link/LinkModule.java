package com.nos.home.module.link;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.ModulePermission;
import org.springframework.stereotype.Component;

@Component
public class LinkModule  extends BaseModule {
    public ModuleType moduleType = ModuleType.SERVICE;

    @Override
    public String getModuleId() {
        return "LINK";
    }

    @Override
    public String getModuleName() {
        return "링크";
    }

    @Override
    public String getDescription() {
        return "단순 내부 또는 외부 링크 모듈";
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public Class<? extends ModulePermission> getPermissionEnum() {
        return LinkModulePermission.class;
    }
}
