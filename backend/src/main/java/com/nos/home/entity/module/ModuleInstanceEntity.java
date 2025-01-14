package com.nos.home.entity.module;


import com.nos.home.common.module.dto.ModuleInstanceDto;
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
    private Long            seq;                    // 메뉴 고유 Sequence

    @Column(name = "module_id", nullable = false, length = 100)
    private String          moduleId;               // 모듈 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false, insertable = false, updatable = false)
    private ModuleEntity    module;                 // 모듈 엔터티

    @Column(name = "instance_id")
    private String          instanceId;             // 인스턴스 ID

    @Column(name = "instance_name", nullable = false, length = 100)
    private String          instanceName;           // 인스턴스 이름

    @Column(name = "config_data", columnDefinition = "json")
    private String          configData;             // 설정 정보

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


    ///=================================================================================================================
    /// Static Methods
    ///=================================================================================================================

    //------------------------------------------------------------------------------------------------------------------
    // 메뉴 엔터티 --> 모듈 인스턴스 엔터티 변환
    //------------------------------------------------------------------------------------------------------------------
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

    //------------------------------------------------------------------------------------------------------------------
    // 모듈 인스턴스 엔터티 --> 모듈 인스턴스 DTO 변환
    //------------------------------------------------------------------------------------------------------------------
    public ModuleInstanceDto toDto()
    {
        return ModuleInstanceDto.builder()
                .moduleId(this.moduleId)                 // 모듈 ID
                .instanceId(this.instanceId)             // 인스턴스 ID
                .instanceName(this.instanceName)         // 인스턴스 이름
                .configData(this.configData)             // 설정 정보
                .build();
    }




    ///=================================================================================================================
    /// Setters
    ///=================================================================================================================
    public void updateConfig(String configData) {
        this.configData = configData;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
