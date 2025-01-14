package com.nos.home.common.menu.service;

import com.nos.home.admin.dto.sitemap.AdMenuRegisterDto;
import com.nos.home.common.menu.repository.MenuRepository;
import com.nos.home.common.util.MenuHelper;
import com.nos.home.common.util.ModuleHelper;
import com.nos.home.entity.module.ModuleInstanceEntity;
import com.nos.home.common.module.repository.ModuleInstanceRepository;
import com.nos.home.entity.menu.MenuEntity;
import com.nos.home.entity.menu.SiteMapEntity;
import com.nos.home.common.menu.dto.SiteMapRequestDto;
import com.nos.home.common.menu.repository.SiteMapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SiteMapService
{
    //------------------------------------------------------------------------------------------------------------------
    private final SiteMapRepository             siteMapRepository;          // 사이트맵 레포지토리
    private final MenuRepository                menuRepository;             // 메뉴 레포지토리
    private final ModuleInstanceRepository      moduleInstanceRepository;   // 모듈 인스턴스 레포지토리
    //------------------------------------------------------------------------------------------------------------------
    // Helper 클래스
    //------------------------------------------------------------------------------------------------------------------
    private final ModuleHelper                  moduleHelper;               // 모듈 헬퍼
    private final MenuHelper                    menuHelper;                 // 메뉴 헬퍼

    @Transactional
    public Long createSiteMap(SiteMapRequestDto siteMapRequest) {
        SiteMapEntity siteMap = new SiteMapEntity();
        siteMap.setTitle(siteMapRequest.getTitle());
        siteMap.setDescription(siteMapRequest.getDescription());
        SiteMapEntity savedSiteMap = siteMapRepository.save(siteMap);
        return savedSiteMap.getSitemapSeq();
    }

    @Transactional
    public List<SiteMapEntity> getAllSiteMaps() {
        return siteMapRepository.findAll();
    }

    @Transactional
    public List<MenuEntity> getAllMenusFromSiteMap(Long sitemapSeq) {
        return menuRepository.findBySitemapSeq(sitemapSeq);
    }

    //------------------------------------------------------------------------------------------------------------------
    // [메뉴 등록] - 사이트맵에 메뉴를 등록한다.
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public MenuEntity createMenu(AdMenuRegisterDto adMenuRegisterDto) {
        //--------------------------------------------------------------------------------------------------------------
        // [신규 메뉴 등록]
        //--------------------------------------------------------------------------------------------------------------
        MenuEntity menu = MenuEntity.of(adMenuRegisterDto);
        menuRepository.save(menu);
        //--------------------------------------------------------------------------------------------------------------
        // [모듈 인스턴스 등록]
        //--------------------------------------------------------------------------------------------------------------
        ModuleInstanceEntity moduleInstance = ModuleInstanceEntity.of(menu);
        moduleInstanceRepository.save(moduleInstance);
        //--------------------------------------------------------------------------------------------------------------
        // DTO 정보 등록 (URL Rewrite 인터셉터에서, 신규 메뉴를 바로 사용할 수 있도록, 메뉴 정보를 저장한다.)
        //--------------------------------------------------------------------------------------------------------------
        menuHelper.addMenu(menu);
        moduleHelper.addModuleInstance(moduleInstance);


        return menu;
    }
}