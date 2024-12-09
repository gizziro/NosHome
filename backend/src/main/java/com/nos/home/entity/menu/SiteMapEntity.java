package com.nos.home.entity.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//----------------------------------------------------------------------------------------------------------------------
// 사이트 맵 엔터티
//----------------------------------------------------------------------------------------------------------------------
@Getter
@Setter
@Entity
@Table(name = "TB_SITE_MAP")
public class SiteMapEntity {
    @Id
    private Long id;

    private String title;

    private String description;
}
