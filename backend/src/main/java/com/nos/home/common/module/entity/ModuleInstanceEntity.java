package com.nos.home.common.module.entity;


import com.nos.home.entity.common.BaseTimeEntity;
import com.nos.home.entity.menu.MenuEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_module_instance")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModuleInstanceEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long            seq;

    @Column(name = "module_id", nullable = false, length = 100)
    private String          moduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false, insertable = false, updatable = false)
    private ModuleEntity    module;

    @Column(name = "instance_id")
    private String          instanceId;

    @Column(name = "instance_name", nullable = false, length = 100)
    private String          instanceName;

    @Column(name = "config_data", columnDefinition = "json")
    private String          configData;

    @Column(nullable = false)
    private Boolean         enabled = true;

    @Builder
    public ModuleInstanceEntity(String moduleId, String instanceId, String instanceName, String configData)
    {
        this.moduleId       = moduleId;
        this.instanceName   = instanceName;
        this.instanceId     = instanceId;
        this.configData     = configData;
        this.enabled        = true;
    }

    public void updateConfig(String configData) {
        this.configData = configData;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static ModuleInstanceEntity of(MenuEntity menuEntity)
    {
        return ModuleInstanceEntity.builder()
                .moduleId(menuEntity.getModuleId())                 // 모듈 ID
                .instanceId(menuEntity.getInstanceId())             // 인스턴스 ID
                .instanceName(menuEntity.getTitle())                // 인스턴스 이름
                .configData("{}")                                   // 설정 정보
                .enabled(true)                                      // 활성화 여부
                .build();
    }

}
