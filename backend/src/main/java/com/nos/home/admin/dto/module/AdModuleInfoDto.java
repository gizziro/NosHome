package com.nos.home.admin.dto.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class AdModuleInfoDto {
    private String moduleId;
    private String moduleType;
    private String moduleName;
    private String description;
}
