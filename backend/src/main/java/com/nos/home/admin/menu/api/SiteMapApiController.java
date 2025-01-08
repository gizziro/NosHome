package com.nos.home.admin.menu.api;

import com.nos.home.entity.menu.SiteMapEntity;
import com.nos.home.common.menu.dto.SiteMapRequestDto;
import com.nos.home.common.menu.service.SiteMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/sitemap")
@RequiredArgsConstructor
public class SiteMapApiController
{
    private final SiteMapService siteMapService;

    @PostMapping("/register")
    public ResponseEntity<Long> addSiteMap(@RequestBody SiteMapRequestDto siteMapRequest) {
        Long sitemapId = siteMapService.createSiteMap(siteMapRequest);
        return ResponseEntity.ok(sitemapId);
    }

    @GetMapping
    public ResponseEntity<List<SiteMapEntity>> getAllSiteMaps() {
        List<SiteMapEntity> siteMaps = siteMapService.getAllSiteMaps();
        return ResponseEntity.ok(siteMaps);
    }
}
