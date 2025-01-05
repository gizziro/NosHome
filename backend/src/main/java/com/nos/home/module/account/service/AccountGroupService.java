package com.nos.home.module.account.service;

import com.nos.home.common.module.repository.ModulePermissionRepository;
import com.nos.home.entity.account.GroupEntity;
import com.nos.home.module.account.repository.AccountGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountGroupService
{
    private final AccountGroupRepository        accountGroupRepository;
    private final ModulePermissionRepository    permissionRepository;

    //------------------------------------------------------------------------------------------------------------------
    // 그룹 존재 여부 체크
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public boolean checkGroupById(String groupId) {
        Optional<GroupEntity> groupEntity = accountGroupRepository.findByGroupId(groupId);
        return groupEntity.isPresent();
    }

    //------------------------------------------------------------------------------------------------------------------
    // 신규 그룹 생성
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public void createGroup(String groupId, String name, String description, boolean isSystemGroup) {
        GroupEntity groupEntity = GroupEntity.of(groupId, name, description, isSystemGroup, true);
        accountGroupRepository.save(groupEntity);
    }

    //------------------------------------------------------------------------------------------------------------------
    // 그룹에 권한 정보 부여
    //------------------------------------------------------------------------------------------------------------------
//    @Transactional
//    public void addModulePermissionsToGroup(String groupId, List<String> permissionCodes)
//    {
//        //--------------------------------------------------------------------------------------------------------------
//        // 그룹 조회
//        //--------------------------------------------------------------------------------------------------------------
//        GroupEntity group = accountGroupRepository.findByGroupId(groupId)
//                .orElseThrow(() -> new RuntimeException("Group not found: " + groupId));
//
//        //--------------------------------------------------------------------------------------------------------------
//        // 현재 그룹이 가진 권한 코드들
//        //--------------------------------------------------------------------------------------------------------------
//        Set<String> existingPermissionCodes = group.getPermissions().stream().map(ModulePermissionEntity::getCode).collect(Collectors.toSet());
//
//        //--------------------------------------------------------------------------------------------------------------
//        // 추가해야 할 권한 코드들 필터링 (이미 있는 권한은 제외)
//        //--------------------------------------------------------------------------------------------------------------
//        List<String> newPermissionCodes = permissionCodes.stream().filter(code -> !existingPermissionCodes.contains(code)).collect(Collectors.toList());
//
//        //--------------------------------------------------------------------------------------------------------------
//        // 추가할 권한이 없으면 종료
//        //--------------------------------------------------------------------------------------------------------------
//        if (newPermissionCodes.isEmpty()) {
//            log.info("모든 권한이 이미 부여되어 있습니다. groupId: {}", groupId);
//            return;
//        }
//        //--------------------------------------------------------------------------------------------------------------
//        // 새로운 권한들 조회 및 할당
//        //--------------------------------------------------------------------------------------------------------------
//        List<ModulePermissionEntity> newPermissions = permissionRepository.findAllByCodeIn(newPermissionCodes);
//        group.getPermissions().addAll(newPermissions);
//
//        accountGroupRepository.save(group);
//
//        log.info("권한이 성공적으로 부여되었습니다. groupId: {}, 추가된 권한 수: {}",
//                groupId, newPermissions.size());
//
//        //--------------------------------------------------------------------------------------------------------------
//        // 상세 로그
//        //--------------------------------------------------------------------------------------------------------------
//        newPermissions.forEach(permission ->
//                log.debug("권한 추가됨 - groupId: {}, permission: {} ({})",
//                        groupId, permission.getName(), permission.getCode())
//        );
//    }
}
