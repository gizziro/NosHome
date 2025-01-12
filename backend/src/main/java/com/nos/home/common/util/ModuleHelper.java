package com.nos.home.common.util;


import com.nos.home.common.menu.repository.MenuRepository;
import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.dto.ModuleTypeDto;
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
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private final ApplicationContext        applicationContext;
    private Map<String, BaseModule>                             modules     = new HashMap<>();      // 전체 모듈 목록
    private Map<BaseModule.ModuleType, Map<String, BaseModule>> moduleMap   = new HashMap<>();      // 용도별 모듈 목록

    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    public void initializeModules()
    {
        log.info("Initializing modules...");
        this.modules = applicationContext.getBeansOfType(BaseModule.class);

        for (BaseModule module : modules.values())
        {
            moduleMap.computeIfAbsent(module.getModuleType(), k -> new HashMap<>()).put(module.getModuleId(), module);
        }
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
}
