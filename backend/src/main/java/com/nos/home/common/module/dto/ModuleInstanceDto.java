package com.nos.home.common.module.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModuleInstanceDto
{
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private String          moduleId;               // 모듈 ID
    private String          instanceId;             // 인스턴스 ID
    private String          instanceName;           // 인스턴스 이름
    private String          configData;             // 설정 정보
}
