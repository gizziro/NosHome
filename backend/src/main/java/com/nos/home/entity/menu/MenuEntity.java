package com.nos.home.entity.menu;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_MENU")
public class MenuEntity {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "sitemap_id")
    private SiteMapEntity sitemap;

    private Long pid;

    private String title;

    private String description;

    private String icon;

    private String url;

    private String type;

    private Boolean activated;

    private int level;

    private int position;
}