package com.nos.home.entity.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//----------------------------------------------------------------------------------------------------------------------
// 사이트 맵 엔터티
//----------------------------------------------------------------------------------------------------------------------
@Getter
@Setter
@Entity
@Table(name = "TB_SITE_MAP")
public class SiteMapEntity
{
    //------------------------------------------------------------------------------------------------------------------
    // 사이트 맵 Sequence 번호
    //------------------------------------------------------------------------------------------------------------------
    @Id
    private Long                sitemapSeq;
    //------------------------------------------------------------------------------------------------------------------
    // 사이트맵 이름
    //------------------------------------------------------------------------------------------------------------------
    private String              title;
    //------------------------------------------------------------------------------------------------------------------
    // 사이트맵 설명
    //------------------------------------------------------------------------------------------------------------------
    private String              description;
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    @CreatedDate
    private LocalDateTime       createdDate;

    @LastModifiedDate
    private LocalDateTime       modifiedDate;
}
