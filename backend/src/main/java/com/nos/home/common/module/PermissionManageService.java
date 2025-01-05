package com.nos.home.common.module;

import com.nos.home.common.module.repository.GroupPermissionMappingRepository;
import com.nos.home.common.module.repository.UserPermissionMappingRepository;
import com.nos.home.entity.permission.GroupPermissionMappingEntity;
import com.nos.home.entity.permission.UserPermissionMappingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionManageService
{
    private final UserPermissionMappingRepository   userPermissionMappingRepository;
    private final GroupPermissionMappingRepository  groupPermissionMappingRepository;

    public void addUserPermission(String userId, String moduleId, String instanceId, String permissionCode)
    {
        UserPermissionMappingEntity entity = UserPermissionMappingEntity.builder()
                .userId(userId)
                .moduleId(moduleId)
                .instanceId(instanceId)
                .permissionCode(permissionCode)
                .build();
        userPermissionMappingRepository.save(entity);
    }

    public void addGroupPermission(String groupId, String moduleId, String instanceId, String permissionCode, String createdBy) {
        GroupPermissionMappingEntity entity = GroupPermissionMappingEntity.builder()
                .groupId(groupId)
                .moduleId(moduleId)
                .instanceId(instanceId)
                .permissionCode(permissionCode)
                .createdBy(createdBy)
                .build();
        groupPermissionMappingRepository.save(entity);
    }

    public List<String> getUserPermissions(String userId, String moduleId, String instanceId) {
        return userPermissionMappingRepository.findByUserIdAndModuleIdAndInstanceId(userId, moduleId, instanceId)
                .stream()
                .map(UserPermissionMappingEntity::getPermissionCode)
                .collect(Collectors.toList());
    }

    public List<String> getGroupPermissions(String groupId, String moduleId, String instanceId) {
        return groupPermissionMappingRepository.findByGroupIdAndModuleIdAndInstanceId(groupId, moduleId, instanceId)
                .stream()
                .map(GroupPermissionMappingEntity::getPermissionCode)
                .collect(Collectors.toList());
    }

    public void removeUserPermission(String userId, String moduleId, String instanceId, String permissionCode) {
        userPermissionMappingRepository.findByUserIdAndModuleIdAndInstanceIdAndPermissionCode(userId, moduleId, instanceId, permissionCode)
                .ifPresent(userPermissionMappingRepository::delete);
    }

    public void removeGroupPermission(String groupId, String moduleId, String instanceId, String permissionCode) {
        groupPermissionMappingRepository.findByGroupIdAndModuleIdAndInstanceIdAndPermissionCode(groupId, moduleId, instanceId, permissionCode)
                .ifPresent(groupPermissionMappingRepository::delete);
    }

    public boolean existsGroupPermission(String groupId, String moduleId, String instanceId, String permissionCode) {
        return groupPermissionMappingRepository
                .findByGroupIdAndModuleIdAndInstanceIdAndPermissionCode(
                        groupId, moduleId, instanceId, permissionCode)
                .isPresent();
    }
}
