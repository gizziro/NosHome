package com.nos.home.common.menu.service;

import com.nos.home.admin.dto.sitemap.AdMenuRegisterDto;
import com.nos.home.common.menu.repository.MenuRepository;
import com.nos.home.entity.menu.MenuEntity;
import com.nos.home.entity.menu.SiteMapEntity;
import com.nos.home.common.menu.dto.SiteMapRequestDto;
import com.nos.home.common.menu.repository.SiteMapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SiteMapService {

    private final SiteMapRepository     siteMapRepository;      // 사이트맵 레포지토리
    private final MenuRepository        menuRepository;         // 메뉴 레포지토리

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

    @Transactional
    public MenuEntity createMenu(AdMenuRegisterDto adMenuRegisterDto) {
        MenuEntity menu = new MenuEntity();
        menu.setSitemapSeq(adMenuRegisterDto.getSitemapSeq());          // sitemap_seq;
        menu.setParentMenuSeq(null);                                      // 상위 메뉴 ID
        menu.setModuleId(adMenuRegisterDto.getModuleId());              // 모듈 ID
        menu.setInstanceId(UUID.randomUUID().toString());               // 인스턴스 ID
        menu.setTitle(adMenuRegisterDto.getTitle());                    // 메뉴명
        menu.setUrl(adMenuRegisterDto.getUrl());                        // URL
        menu.setTarget(adMenuRegisterDto.getTarget());                  // 타겟
        menu.setDescription(adMenuRegisterDto.getDescription());        // 설명
        menu.setIcon(null);                                             // 아이콘
        menu.setDepth(1);                                               // 깊이
        menu.setPosition(0);
        menu.setEnabled(true);                                          // 활성화 여부

        menuRepository.save(menu);
        return menu;
    }
}