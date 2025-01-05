package com.nos.home.module.menu;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.ModulePermission;
import com.nos.home.module.board.BoardModulePermission;
import org.springframework.stereotype.Component;

@Component
public class MenuModule extends BaseModule {
    public ModuleType moduleType = ModuleType.SYSTEM;

    @Override
    public String getModuleId() {
        return "MENU";
    }

    @Override
    public String getModuleName() {
        return "메뉴관리";
    }

    @Override
    public String getDescription() {
        return "시스템 메뉴 관리 기능";
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public Class<? extends ModulePermission> getPermissionEnum() {
        return MenuModulePermission.class;
    }
}
