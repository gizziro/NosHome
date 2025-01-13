package com.nos.home.admin.dto.sitemap;

import com.nos.home.entity.menu.MenuEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdMenuListDto {
    private Long                    menuSeq;
    private String                  title;
    private String                  url;
    private List<AdMenuListDto>     menus;
    private boolean                 enabled;

    public static AdMenuListDto of(MenuEntity menuEntity) {
        AdMenuListDto adMenuListDto = new AdMenuListDto();
        adMenuListDto.setMenuSeq(menuEntity.getMenuSeq());
        adMenuListDto.setTitle(menuEntity.getTitle());
        adMenuListDto.setUrl(menuEntity.getUrl());
        adMenuListDto.setEnabled(menuEntity.getEnabled());
        adMenuListDto.menus = new ArrayList<>();
        return adMenuListDto;
    }
}
