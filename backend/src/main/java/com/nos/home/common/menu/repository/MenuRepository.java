package com.nos.home.common.menu.repository;

import com.nos.home.entity.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    // 특정 사이트맵의 모든 메뉴 조회
    List<MenuEntity> findBySitemapSeq(Long sitemapSeq);

    // 특정 사이트맵의 최상위 메뉴 조회 (parentMenuSeq가 null인 경우)
    List<MenuEntity> findBySitemapSeqAndParentMenuSeqIsNullOrderByPositionAsc(Long sitemapSeq);

    // 특정 메뉴의 하위 메뉴 조회
    List<MenuEntity> findByParentMenuSeqOrderByPositionAsc(Long parentMenuSeq);

    // URL로 메뉴 조회
    Optional<MenuEntity> findByUrl(String url);

    // 특정 모듈의 메뉴 목록 조회
    List<MenuEntity> findByModuleId(String moduleId);

    // 특정 모듈 인스턴스의 메뉴 조회
    Optional<MenuEntity> findByModuleIdAndInstanceId(String moduleId, String instanceId);

    // 활성화된 메뉴만 조회
    List<MenuEntity> findByEnabledTrueOrderByPositionAsc();

    // 특정 depth의 메뉴 조회
    List<MenuEntity> findByDepthOrderByPositionAsc(Integer depth);

    // 메뉴 순서 변경을 위한 position 기준 조회
    List<MenuEntity> findBySitemapSeqAndDepthOrderByPositionAsc(Long sitemapSeq, Integer depth);
}
