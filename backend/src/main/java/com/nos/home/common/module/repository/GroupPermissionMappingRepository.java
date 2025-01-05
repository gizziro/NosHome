package com.nos.home.common.module.repository;

import com.nos.home.entity.permission.GroupPermissionMappingEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupPermissionMappingRepository extends JpaRepository<GroupPermissionMappingEntity, Long>
{
    //------------------------------------------------------------------------------------------------------------------
    // 그룹 아이디로 권한 매핑 정보 조회
    //------------------------------------------------------------------------------------------------------------------
    List<GroupPermissionMappingEntity>      findByGroupId(String groupId);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 그룹, 2) 특정 모듈에 대한 모든 권한 조회
    //------------------------------------------------------------------------------------------------------------------
    List<GroupPermissionMappingEntity>      findByGroupIdAndModuleId(String groupId, String moduleId);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 그룹, 2) 특정 모듈, 3) 특정 인스턴스에 대한 모든 권한 조회
    //------------------------------------------------------------------------------------------------------------------
    List<GroupPermissionMappingEntity>      findByGroupIdAndModuleIdAndInstanceId(String groupId, String moduleId, String instanceId);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 그룹, 2) 특정 모듈, 3) 특정 인스턴스, 4) 특정 권한 코드로 정보 조회
    //------------------------------------------------------------------------------------------------------------------
    Optional<GroupPermissionMappingEntity>  findByGroupIdAndModuleIdAndInstanceIdAndPermissionCode(String groupId, String moduleId, String instanceId, String permissionCode);

    //------------------------------------------------------------------------------------------------------------------
    // 1) 특정 그룹의 2) 특정 시퀀스에 대한 권한 정보 삭제
    //------------------------------------------------------------------------------------------------------------------
    void deleteAllByGroupIdAndSeqIn(String groupId, List<Long> seqs);
}
