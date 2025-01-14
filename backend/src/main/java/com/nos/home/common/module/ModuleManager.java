package com.nos.home.common.module;

import com.nos.home.common.util.MenuHelper;
import com.nos.home.common.util.ModuleHelper;
import com.nos.home.entity.module.ModuleEntity;
import com.nos.home.entity.module.ModulePermissionEntity;
import com.nos.home.common.module.repository.ModulePermissionRepository;
import com.nos.home.common.module.repository.ModuleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class ModuleManager {
    ///=================================================================================================================
    /// [1] 필드 및 레포지토리
    ///=================================================================================================================
    private final ApplicationContext                applicationContext;
    private final ModuleRepository                  moduleRepository;
    private final ModulePermissionRepository        permissionRepository;
    private final ModuleHelper                      moduleHelper;
    private final MenuHelper                        menuHelper;

    @PostConstruct
    public void initialize() {
        log.debug("========================================");
        //--------------------------------------------------------------------------------------------------------------
        // 모듈 목록 및 모듈 인스턴스 초기화
        //--------------------------------------------------------------------------------------------------------------
        moduleHelper.initialize();
        menuHelper.initialize();



        syncModules(moduleHelper.getModules());
        log.debug("========================================");
    }

    private void syncModules(Collection<BaseModule> moduleImpls) {
        //--------------------------------------------------------------------------------------------------------------
        // 1. 현재 DB에 저장된 모든 모듈과 권한 정보 조회
        //--------------------------------------------------------------------------------------------------------------
        List<ModuleEntity>          existingModules = moduleRepository.findAll();
        Map<String, ModuleEntity>   moduleMap       = existingModules.stream().collect(Collectors.toMap(ModuleEntity::getModuleId, m -> m));

        List<ModulePermissionEntity> existingPermissions = permissionRepository.findAll();
        Map<String, ModulePermissionEntity> permissionMap = existingPermissions.stream().collect(Collectors.toMap(ModulePermissionEntity::getPermissionCode, p -> p));

        //--------------------------------------------------------------------------------------------------------------
        // 2. 모듈과 권한 일괄 처리
        //--------------------------------------------------------------------------------------------------------------
        List<ModuleEntity>              modulesToSave       = new ArrayList<>();
        List<ModulePermissionEntity>    permissionsToSave   = new ArrayList<>();

        for (BaseModule moduleImpl : moduleImpls) {
            BaseModule.ModuleType moduleType = moduleImpl.getModuleType();
            log.debug("모듈명 : {} ({}) 서비스 모듈 여부 : {}", moduleImpl.getModuleName(), moduleImpl.getModuleId(), moduleType);

            // 모듈 처리
            ModuleEntity module = moduleMap.getOrDefault(
                    moduleImpl.getModuleId(),
                    new ModuleEntity()
            );

            // 모듈 정보 기입
            module.setModuleId(moduleImpl.getModuleId());           // 모듈 고유 ID
            module.setModuleType(moduleType);                       // 모듈 타입
            module.setName(moduleImpl.getModuleName());             // 모듈명
            module.setDescription(moduleImpl.getDescription());     // 모듈 설명
            // 모듈 저장 목록에 추가
            modulesToSave.add(module);

            // 권한 처리
            Class<? extends ModulePermission> permEnum = moduleImpl.getPermissionEnum();
            ModulePermission[] permissions = permEnum.getEnumConstants();

            for (ModulePermission permission : permissions) {
                ModulePermissionEntity permEntity = permissionMap.getOrDefault(
                        permission.getCode(),
                        new ModulePermissionEntity()
                );

                permEntity.setModule(module);
                permEntity.setPermissionCode(permission.getCode());
                permEntity.setName(permission.getName());
                permEntity.setDescription(permission.getDescription());

                permissionsToSave.add(permEntity);
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        // 3. 일괄 저장
        //--------------------------------------------------------------------------------------------------------------
        moduleRepository.saveAll(modulesToSave);
        permissionRepository.saveAll(permissionsToSave);

        //--------------------------------------------------------------------------------------------------------------
        // 4. 더 이상 사용되지 않는 권한 삭제 (선택적)
        //--------------------------------------------------------------------------------------------------------------
        Set<String> currentPermissionCodes = permissionsToSave.stream()
                .map(ModulePermissionEntity::getPermissionCode)
                .collect(Collectors.toSet());

        List<ModulePermissionEntity> permissionsToDelete = existingPermissions.stream()
                .filter(p -> !currentPermissionCodes.contains(p.getPermissionCode()))
                .collect(Collectors.toList());

        if (!permissionsToDelete.isEmpty()) {
            permissionRepository.deleteAll(permissionsToDelete);
        }
    }
}


