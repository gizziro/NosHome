package com.nos.home.common.util;


import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.dto.ModuleInstanceDto;
import com.nos.home.common.module.dto.ModuleTypeDto;
import com.nos.home.common.module.repository.ModuleInstanceRepository;
import com.nos.home.entity.module.ModuleInstanceEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ModuleHelper
{
    ///=================================================================================================================
    /// [1] 필드 및 레포지토리
    ///=================================================================================================================
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private final   ApplicationContext                                      applicationContext;                 // 어플리케이션 컨텍스트
    //------------------------------------------------------------------------------------------------------------------
    // 전체 모듈 목록
    //------------------------------------------------------------------------------------------------------------------
    private         Map<String, BaseModule>                                 modules;
    private         Map<BaseModule.ModuleType, Map<String, BaseModule>>     moduleMap;
    private         Map<String, ModuleInstanceDto>                          moduleInstances;
    //------------------------------------------------------------------------------------------------------------------
    // 레포지토리
    //------------------------------------------------------------------------------------------------------------------
    private final ModuleInstanceRepository                                  moduleInstanceRepository;


    @PostConstruct
    public void prepare()
    {
        log.debug("[ModuleHelper] 내부 변수 생성");
        // 전체 모듈 목록
        this.modules                = applicationContext.getBeansOfType(BaseModule.class);
        // 모듈 특성별 모듈 목록
        this.moduleMap              = new HashMap<>();
        // 모듈 인스턴스 목록
        this.moduleInstances        = new HashMap<>();
    }


    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    public void initialize()
    {
        //--------------------------------------------------------------------------------------------------------------
        // 서비스 용도 별로 모듈을 분류
        //--------------------------------------------------------------------------------------------------------------
        for (BaseModule module : modules.values())
        {
            moduleMap.computeIfAbsent(module.getModuleType(), k -> new HashMap<>()).put(module.getModuleId(), module);
        }
        //--------------------------------------------------------------------------------------------------------------
        // 모듈 인스턴스 조회 및 출력
        //--------------------------------------------------------------------------------------------------------------
        moduleInstanceRepository.findAll().forEach(m -> moduleInstances.put(m.getInstanceId(), m.toDto()));
        log.info("moduleInstances: {}", moduleInstances.size());
        log.info("[ModuleHelper] 모듈 초기화 완료");
    }

    ///=================================================================================================================
    /// [2] 필드, 생성자
    ///=================================================================================================================

    //------------------------------------------------------------------------------------------------------------------
    // [모듈 인스턴스 조회] : Instance ID로 조회
    //------------------------------------------------------------------------------------------------------------------
    public ModuleInstanceDto getModuleInstance(String instanceId)
    {
        return moduleInstances.get(instanceId);
    }

    //------------------------------------------------------------------------------------------------------------------
    // 전체 모듈 목록 조회
    //------------------------------------------------------------------------------------------------------------------
    public List<BaseModule> getModules()
    {
        return List.copyOf(modules.values());
    }

    //------------------------------------------------------------------------------------------------------------------
    // [사용자가 지정한 모듈 타입에 따른, 모듈 목록 반환]
    //------------------------------------------------------------------------------------------------------------------
    public List<ModuleTypeDto> getModuleDtos(BaseModule.ModuleType moduleType)
    {
        return moduleMap.get(moduleType).values().stream().map(
                m -> new ModuleTypeDto(m.getModuleType().toString(), m.getModuleName(), m.getModuleId(), m.getDescription())
        ).toList();
    }

    //------------------------------------------------------------------------------------------------------------------
    // [모듈 인스턴스 추가]
    //------------------------------------------------------------------------------------------------------------------
    public ModuleInstanceDto addModuleInstance(ModuleInstanceEntity moduleInstance)
    {
        ModuleInstanceDto returnDto = moduleInstance.toDto();
        moduleInstances.put(moduleInstance.getInstanceId(), returnDto);
        return returnDto;
    }
}

