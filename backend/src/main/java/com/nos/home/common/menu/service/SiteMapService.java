package com.nos.home.common.menu.service;

import com.nos.home.admin.dto.sitemap.AdMenuRegisterDto;
import com.nos.home.common.menu.repository.MenuRepository;
import com.nos.home.common.module.entity.ModuleInstanceEntity;
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
import java.util.UUID;

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
        log.info("New Menu: {}", menu);
        //--------------------------------------------------------------------------------------------------------------
        // [모듈 인스턴스 등록]
        //--------------------------------------------------------------------------------------------------------------
        ModuleInstanceEntity moduleInstance = ModuleInstanceEntity.of(menu);
        log.info("New Module Instance: {}", moduleInstance);
        moduleInstanceRepository.save(moduleInstance);

        return menu;
    }
}