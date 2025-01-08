package com.nos.home.common.module;

import jakarta.persistence.MappedSuperclass;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@MappedSuperclass
public abstract class BaseModule {
    public enum ModuleType {
        SYSTEM, ADMIN, SERVICE
    }

    // 모듈 정보를 가져오는 추상 메서드들
    public abstract String          getModuleId();
    public abstract String          getModuleName();
    public abstract String          getDescription();
    public abstract ModuleType      getModuleType();

    // 모듈별 권한 ENUM 타입을 반환
    public abstract Class<? extends ModulePermission> getPermissionEnum();

}
