package com.nos.home.entity.permission;

import com.nos.home.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasePermissionMapping extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long        seq;

    @Column(name = "module_id", nullable = false)
    private String      moduleId;

    @Column(name = "instance_id")
    private String      instanceId;

    @Column(name = "permission_code", nullable = false, length = 100)
    private String      permissionCode;

    public String getModulePermission(){
        String instance = instanceId == null ? "system" : instanceId;
        return moduleId + "_" + instance + "_" + permissionCode;
    }
}