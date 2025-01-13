package com.nos.home.admin.api;

import com.nos.home.admin.dto.sitemap.AdMenuListDto;
import com.nos.home.admin.dto.sitemap.AdMenuRegisterDto;
import com.nos.home.common.response.ApiResponse;
import com.nos.home.common.security.CurrentUser;
import com.nos.home.common.security.details.AccountDto;
import com.nos.home.entity.menu.MenuEntity;
import com.nos.home.entity.menu.SiteMapEntity;
import com.nos.home.common.menu.dto.SiteMapRequestDto;
import com.nos.home.common.menu.service.SiteMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/sitemap")
@RequiredArgsConstructor
public class SiteMapApiController
{
    private final SiteMapService siteMapService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody SiteMapRequestDto siteMapRequest) {
        Long sitemapId = siteMapService.createSiteMap(siteMapRequest);
        return ResponseEntity.ok(sitemapId);
    }

    @GetMapping("/all")
    public ApiResponse<List<SiteMapEntity>> getAllSiteMaps(@CurrentUser AccountDto account)
    {
        // 사용자 로그인 체크

        // 관리자 권한 체크

        //
        List<SiteMapEntity> siteMaps = siteMapService.getAllSiteMaps();
        return ApiResponse.OK(siteMaps);
    }

    //------------------------------------------------------------------------------------------------------------------
    // [사이트맵 메뉴 목록 조회]
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/{sitemapSeq}")
    public ApiResponse<List<AdMenuListDto>> getSiteMap(@PathVariable Long sitemapSeq)
    {
        /// 1. 사이트맵 유효성 체크

        List<MenuEntity> menuEntities = siteMapService.getAllMenusFromSiteMap(sitemapSeq);

        List<AdMenuListDto> retList = new ArrayList<>();
        for(MenuEntity menuEntity : menuEntities)
        {
            AdMenuListDto adMenuListDto = AdMenuListDto.of(menuEntity);
            retList.add(adMenuListDto);
        }

        return ApiResponse.OK(retList);
    }


    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    @PostMapping("/{sitemapSeq}/register")
    public ResponseEntity<MenuEntity> registerMenu(@PathVariable Long sitemapSeq, @RequestBody AdMenuRegisterDto adMenuRegisterDto)
    {


        MenuEntity newMenu = siteMapService.createMenu(adMenuRegisterDto);
        return ResponseEntity.ok(newMenu);
    }




}
