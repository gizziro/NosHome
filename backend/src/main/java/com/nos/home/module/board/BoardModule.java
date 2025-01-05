package com.nos.home.module.board;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.ModulePermission;
import org.springframework.stereotype.Component;

@Component
public class BoardModule extends BaseModule {
    public ModuleType moduleType = ModuleType.SERVICE;

    @Override
    public String getModuleId() {
        return "BOARD";
    }

    @Override
    public String getModuleName() {
        return "게시판";
    }

    @Override
    public String getDescription() {
        return "게시글 작성 및 관리 모듈";
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public Class<? extends ModulePermission> getPermissionEnum() {
        return BoardModulePermission.class;
    }
}
