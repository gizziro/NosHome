package com.nos.home.common.menu.repository;

import com.nos.home.entity.menu.SiteMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteMapRepository extends JpaRepository<SiteMapEntity, Long>
{
    Optional<SiteMapEntity> findBySitemapSeq(Long sitemapSeq);
    Optional<SiteMapEntity> findByTitle(String title);
}