package com.nos.home.common.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Set;

@Getter
@AllArgsConstructor
public class ModuleEndpoint {
    private String                      pattern;            // URL 패턴 (예: "/write", "/{id}")
    private HttpMethod                  method;             // HTTP 메서드
    private String                      handlerMethod;      // 처리할 메서드 이름
    private Set<ModulePermission>       permissions;        // 필요한 권한 목록
}
