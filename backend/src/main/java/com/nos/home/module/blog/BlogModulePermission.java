package com.nos.home.module.blog;

import com.nos.home.common.module.ModulePermission;

public enum BlogModulePermission implements ModulePermission {
    POST("BLOG_POST", "블로그 포스팅 권한", "블로그 게시글을 작성합니다.");

    private final String code;
    private final String name;
    private final String description;

    BlogModulePermission(String code, String name, String description) {
        this.code           = code;
        this.name           = name;
        this.description    = description;
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
