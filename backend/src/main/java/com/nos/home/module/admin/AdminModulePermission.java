package com.nos.home.module.admin;

import com.nos.home.common.module.ModulePermission;

public enum AdminModulePermission implements ModulePermission {
    ACCESS_ADMIN("ACCESS_ADMIN","관리자 페이지 접근","관리자 페이지에 접근을 제어합니다."),
    ACCESS_ACCOUNT("ACCESS_ACCOUNT","계정 관리 접근","계정 관리 페이지에 접근을 제어합니다."),;

    private final String code;
    private final String name;
    private final String description;

    AdminModulePermission(String code, String name, String description) {
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