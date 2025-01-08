package com.nos.home.common.module.repository;

import com.nos.home.common.module.entity.ModulePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ModulePermissionRepository extends JpaRepository<ModulePermissionEntity, Long>
{
    //------------------------------------------------------------------------------------------------------------------
    // 단일 code로 ModulePermissionEntity 조회
    //------------------------------------------------------------------------------------------------------------------
    Optional<ModulePermissionEntity> findByPermissionCode(String code);

    //------------------------------------------------------------------------------------------------------------------
    // 여러 code로 ModulePermissionEntity 목록 조회
    //------------------------------------------------------------------------------------------------------------------
    List<ModulePermissionEntity> findAllByPermissionCodeIn(Collection<String> codes);
}
