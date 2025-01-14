package com.nos.home.entity.module;

import com.nos.home.common.module.BaseModule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_modules")
@Getter
@Setter
public class ModuleEntity {
    @Id
    @Column(name = "module_id", length = 100)
    private String moduleId;

    @Column(name = "module_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private BaseModule.ModuleType moduleType;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 250)
    private String description;

    @OneToMany(mappedBy = "module")
    private Set<ModuleInstanceEntity> instances = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModulePermissionEntity> permissions = new ArrayList<>();
}
