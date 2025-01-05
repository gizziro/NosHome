package com.nos.home.module.account;

import com.nos.home.common.module.ModulePermission;

public enum AccountModulePermission implements ModulePermission {
    ACCESS_USER_MYPAGE("ACCESS_USER_MYPAGE","마이페이지 접근","마이페이지에 접근할 수 있습니다.");

    private final String code;
    private final String name;
    private final String description;

    AccountModulePermission(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}