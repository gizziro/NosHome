package com.nos.home.common.menu.dto;

import com.nos.home.common.module.dto.ModuleInstanceDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDto {
    private Long                        menuSeq;
    private Long                        sitemapSeq;
    private Long                        parentMenuSeq;
    private String                      moduleId;
    private String                      instanceId;
    private String                      title;
    private String                      url;
    private String                      target;
    private String                      description;
    private String                      icon;
    private ModuleInstanceDto           moduleInstance;
}
