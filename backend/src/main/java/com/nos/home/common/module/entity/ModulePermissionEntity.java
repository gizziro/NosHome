package com.nos.home.common.module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "tb_module_permissions")
@Getter
@Setter
public class ModulePermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long                id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleEntity        module;

    @Column(name = "permission_code", nullable = false, length = 100)
    private String              permissionCode;

    @Column(nullable = false, length = 255)
    private String              name;

    @Column(length = 500)
    private String              description;
}
