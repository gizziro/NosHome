package com.nos.home.module.menu;

import com.nos.home.common.module.ModulePermission;

public enum MenuModulePermission implements ModulePermission
{
    CREATE_MENU("CREATE_MENU", "메뉴 생성 권한", "메뉴를 생성할 수 있는 권한입니다."),
    MODIFY_MENU("MODIFY_MENU", "메뉴 수정 권한", "메뉴를 수정할 수 있는 권한입니다."),
    DELETE_MENU("DELETE_MENU", "메뉴 삭제 권한", "메뉴를 삭제할 수 있는 권한입니다."),
    SHOW_MENU("SHOW_MENU", "메뉴 조회 권한", "메뉴를 조회할 수 있는 권한입니다."),
    ACCESS_MENU("ACCESS_MENU", "메뉴 접근 권한", "메뉴 관리 페이지에 접근할 수 있는 권한입니다.");

    private final String code;
    private final String name;
    private final String description;

    MenuModulePermission(String code, String name, String description) {
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
