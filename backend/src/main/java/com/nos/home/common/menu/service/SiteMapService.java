package com.nos.home.common.menu.service;

import com.nos.home.entity.menu.SiteMapEntity;
import com.nos.home.common.menu.dto.SiteMapRequestDto;
import com.nos.home.common.menu.repository.SiteMapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteMapService {

    private final SiteMapRepository siteMapRepository;

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
}