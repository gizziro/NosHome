package com.nos.home.admin.menu.view;

import com.nos.home.common.menu.service.SiteMapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sitemap")
public class SiteMapController {

    private final SiteMapService siteMapService;

    public SiteMapController(SiteMapService siteMapService) {
        this.siteMapService = siteMapService;
    }

    //------------------------------------------------------------------------------------------------------------------
    // [사이트맵 목록 화면]
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/list")
    public String viewSiteMapPage(Model model)
    {
        model.addAttribute("sitemaps", siteMapService.getAllSiteMaps());
        return "admin/menu/sitemap";
    }
}
