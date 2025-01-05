package com.nos.home.common.module.repository;

import com.nos.home.entity.permission.UserPermissionMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPermissionMappingRepository extends JpaRepository<UserPermissionMappingEntity, Long>
{
    //------------------------------------------------------------------------------------------------------------------
    // 사용자 아이디로 권한 매핑 정보 조회
    //------------------------------------------------------------------------------------------------------------------
    List<UserPermissionMappingEntity>   findByUserId(String userId);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 사용자, 2) 특정 모듈에 대한 모든 권한 조회
    //------------------------------------------------------------------------------------------------------------------
    List<UserPermissionMappingEntity> findByUserIdAndModuleId(String userId, String moduleId);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 사용자, 2) 특정 모듈, 3) 특정 인스턴스에 대한 모든 권한 조회
    //------------------------------------------------------------------------------------------------------------------
    List<UserPermissionMappingEntity> findByUserIdAndModuleIdAndInstanceId(String userId, String moduleId, String instanceId);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 사용자, 2) 특정 모듈, 3) 특정 인스턴스, 4) 특정 권한 코드로 정보 조회
    //------------------------------------------------------------------------------------------------------------------
    Optional<UserPermissionMappingEntity> findByUserIdAndModuleIdAndInstanceIdAndPermissionCode(String userId, String moduleId, String instanceId, String permissionCode);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 사용자의 2) 특정 시퀀스에 대한 권한 정보 삭제
    //------------------------------------------------------------------------------------------------------------------
    void deleteAllByUserIdAndSeqIn(String userId, List<Long> seqs);
}

