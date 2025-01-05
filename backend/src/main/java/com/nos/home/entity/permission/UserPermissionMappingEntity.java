package com.nos.home.entity.permission;

import com.nos.home.entity.account.AccountEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user_permission_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPermissionMappingEntity extends BasePermissionMapping  {

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AccountEntity user;

    @Builder
    public UserPermissionMappingEntity(String userId, String moduleId, String instanceId, String permissionCode) {
        this.userId = userId;
        this.setModuleId(moduleId);
        this.setInstanceId(instanceId);
        this.setPermissionCode(permissionCode);
    }
}
