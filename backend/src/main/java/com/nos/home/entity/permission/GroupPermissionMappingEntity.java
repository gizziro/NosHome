package com.nos.home.entity.permission;

import com.nos.home.entity.account.GroupEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_group_permission_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupPermissionMappingEntity extends BasePermissionMapping
{
    @Column(name = "group_id", nullable = false, length = 50)
    private String groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private GroupEntity group;

    @Builder
    public GroupPermissionMappingEntity(String groupId, String moduleId, String instanceId, String permissionCode, String createdBy) {
        this.groupId = groupId;
        this.setModuleId(moduleId);
        this.setInstanceId(instanceId);
        this.setPermissionCode(permissionCode);
        this.setCreatedBy(createdBy);
    }
}
