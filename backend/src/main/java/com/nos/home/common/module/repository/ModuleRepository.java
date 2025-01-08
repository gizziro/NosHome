package com.nos.home.common.module.repository;

import com.nos.home.common.module.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
    // 모듈 코드 번호를 이용하여서 모듈을 찾는 메서드

    Optional<ModuleEntity> findByModuleId(String code);
}
