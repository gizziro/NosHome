package com.nos.home.module.board;

import com.nos.home.common.module.ModulePermission;

public enum BoardModulePermission implements ModulePermission {
    CREATE_ARTICLE("CREATE_ARTICLE", "게시글 작성 권한", "게시판의 게시글을 작성할 수 있습니다."),
    READ_ARTICLE("READ_ARTICLE", "게시글 조회 권한", "게시판의 게시글을 조회할 수 있습니다."),
    MODIFY_ARTICLE("MODIFY_ARTICLE", "게시글 수정 권한", "게시판의 게시글을 수정할 수 있습니다."),
    DELETE_ARTICLE("BOARD_DELETE", "게시글 삭제 권한", "게시판의 게시글을 삭제할 수 있습니다."),;

    private final String code;
    private final String name;
    private final String description;

    BoardModulePermission(String code, String name, String description) {
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
