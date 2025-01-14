package com.nos.home.common.module.repository;

import com.nos.home.entity.module.ModuleInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleInstanceRepository  extends JpaRepository<ModuleInstanceEntity, Long> {

}
