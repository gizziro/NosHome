package com.nos.home.admin.dto.sitemap;

import lombok.Data;

@Data
public class AdMenuRegisterDto {
    private Long sitemapSeq;
    private String moduleId;
    private String title;
    private String url;
    private String target;
    private String description;
}
