package com.nos.home.common.util;

import com.nos.home.common.menu.dto.MenuDto;
import com.nos.home.common.menu.repository.MenuRepository;
import com.nos.home.common.module.repository.ModuleInstanceRepository;
import com.nos.home.entity.menu.MenuEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuHelper
{
    ///=================================================================================================================
    /// [1] 필드 및 레포지토리
    ///=================================================================================================================
    private         Map<String, MenuDto>    menuMap             = new HashMap<>();
    private final   ModuleHelper            moduleHelper;
    //------------------------------------------------------------------------------------------------------------------
    // 레포지토리
    //------------------------------------------------------------------------------------------------------------------
    private final MenuRepository menuRepository;

    //------------------------------------------------------------------------------------------------------------------
    // 시스템 로드 후, 메뉴 정보 로딩 및 모듈 인스턴스 연결
    //------------------------------------------------------------------------------------------------------------------
    public void initialize()
    {
        //--------------------------------------------------------------------------------------------------------------
        // 메뉴 URL 정보 저장
        //--------------------------------------------------------------------------------------------------------------
        menuRepository.findAll().forEach(menu -> {
            MenuDto dto = MenuEntity.toDto(menu);
            dto.setModuleInstance(moduleHelper.getModuleInstance(menu.getInstanceId()));
            menuMap.put(menu.getUrl(), dto);
        });
    }

    public MenuDto checkSlug(String slug)
    {
        return menuMap.get(slug);
    }

    public MenuDto addMenu(MenuEntity menu)
    {
        MenuDto dto = MenuEntity.toDto(menu);
        dto.setModuleInstance(moduleHelper.getModuleInstance(menu.getInstanceId()));
        menuMap.put(menu.getUrl(), dto);
        return dto;
    }


}
