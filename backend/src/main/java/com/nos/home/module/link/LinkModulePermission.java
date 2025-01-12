package com.nos.home.module.link;

import com.nos.home.common.module.ModulePermission;

public enum LinkModulePermission implements ModulePermission {
    CREATE_ARTICLE("CREATE_LINK", "게시글 작성 권한", "게시판의 게시글을 작성할 수 있습니다.");

    private final String code;
    private final String name;
    private final String description;

    LinkModulePermission(String code, String name, String description) {
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
