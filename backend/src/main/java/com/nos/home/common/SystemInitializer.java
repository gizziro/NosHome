package com.nos.home.common;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.module.ModulePermission;
import com.nos.home.common.module.PermissionManageService;
import com.nos.home.module.account.repository.AccountRepository;
import com.nos.home.module.account.service.AccountGroupService;
import com.nos.home.module.account.service.AccountService;
import com.nos.home.module.admin.AdminModulePermission;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(2)
@RequiredArgsConstructor
public class SystemInitializer {
    //---------------------------------------------------*-98/---------------------------------------------------------------
    // 서비스
    //------------------------------------------------------------------------------------------------------------------
    private final ApplicationContext        applicationContext;
    private final AccountService            accountService;
    private final AccountGroupService       accountGroupService;
    private final PermissionManageService   permissionManageService;

    //------------------------------------------------------------------------------------------------------------------
    // 관리자 초기 계정 정보
    //------------------------------------------------------------------------------------------------------------------
    @Value("${system.admin.userId}")
    private String  adminUserId;

    @Value("${system.admin.password}")
    private String  adminPassword;

    @Value("${system.admin.phone}")
    private String  adminPhone;
    //------------------------------------------------------------------------------------------------------------------


    @PostConstruct
    public void initialize()
    {
        //--------------------------------------------------------------------------------------------------------------
        // 기본 그룹 존재여부를 체크 하고, 없다면 기본그룹을 생성한다.
        //--------------------------------------------------------------------------------------------------------------
        checkDefaultGroup();

        //--------------------------------------------------------------------------------------------------------------
        // 관리자 계정을 생성한다.
        //--------------------------------------------------------------------------------------------------------------
        checkAdminAccount();

        //--------------------------------------------------------------------------------------------------------------
        // 관리자 그룹에 관리자 계정을 추가 한다.
        //--------------------------------------------------------------------------------------------------------------
        checkAdminAccountGroup();

        //--------------------------------------------------------------------------------------------------------------
        // 관리자 그룹에 기본 관리자 권한을 부여한다.
        //--------------------------------------------------------------------------------------------------------------
        checkAdminGroupPermissions();
    }

    //------------------------------------------------------------------------------------------------------------------
    // 기본 그룹 생성
    //------------------------------------------------------------------------------------------------------------------
    private void checkDefaultGroup()
    {
        createSystemGroupIfNotExists("user", "일반 사용자", "회원 가입 시 기본적으로 할당되는 사용자 그룹", true);
        createSystemGroupIfNotExists("administrator", "관리자", "시스템 관리자 페이지 접근 가능한 사용자 그룹", true);
    }

    //------------------------------------------------------------------------------------------------------------------
    // 그룹의 존재 여부를 체크하고, 그룹을 생성한다.
    //------------------------------------------------------------------------------------------------------------------
    private void createSystemGroupIfNotExists(String groupId, String name, String description, boolean isSystemGroup)
    {
        if(!accountGroupService.checkGroupById(groupId))
        {
            log.info("그룹 {} ({})이 존재하지 않습니다. 기본 그룹을 생성합니다.", name, groupId);
            accountGroupService.createGroup(groupId, name, description, isSystemGroup);
        }
        else
        {
            log.info("그룹 {} ({})이 이미 존재합니다.", name, groupId);
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // 관리자 계정 생성
    //------------------------------------------------------------------------------------------------------------------
    private void checkAdminAccount()
    {
        //--------------------------------------------------------------------------------------------------------------
        // 관리자 계정 존재여부 체크
        //--------------------------------------------------------------------------------------------------------------
        if (!accountService.checkId(adminUserId))
        {
            log.info("관리자 계정이 존재하지 않습니다. 관리자 계정을 생성합니다. userId : {}", adminUserId);
            accountService.createAdminAccount(adminUserId, adminPassword, adminPhone);
        }
        else
        {
            log.info("관리자 계정이 이미 존재합니다.");
        }
    }

    private  void checkAdminAccountGroup()
    {
        assignAdminToAdminGroup("administrator");
        assignAdminToAdminGroup("user");
    }

    //------------------------------------------------------------------------------------------------------------------
    // 관리자 그룹에 관리자 계정을 추가한다.
    //------------------------------------------------------------------------------------------------------------------
    private void assignAdminToAdminGroup(String adminGroupId) {
        try
        {
            // 관리자가 이미 관리자 그룹에 속해있는지 확인
            if (!accountService.isUserInGroup(adminUserId, adminGroupId))
            {
                log.debug("관리자 계정을 관리자 그룹에 추가합니다. userId: {}", adminUserId);
                accountService.addUserToGroup(adminUserId, adminGroupId, "SYSTEM");
                log.debug("관리자 계정이 관리자 그룹에 성공적으로 추가되었습니다.");
            }
            else
            {
                log.debug("관리자 계정이 이미 관리자 그룹에 속해 있습니다.");
            }
        }
        catch (Exception e)
        {
            log.error("관리자 계정을 관리자 그룹에 추가하는 중 오류 발생", e);
            throw new RuntimeException("Failed to assign admin user to admin group", e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // 관리자 그룹에 권한을 부여한다.
    //------------------------------------------------------------------------------------------------------------------
    private  void checkAdminGroupPermissions()
    {
        String adminGroupId = "administrator";

        try {
            Map<String, BaseModule> modules = applicationContext.getBeansOfType(BaseModule.class);
            log.info("관리자 권한을 부여할 모듈 수: {}", modules.size());

            // ADMIN 타입의 모듈들에 대해서만 권한 부여
            modules.values().stream()
                    .filter(module -> module.getModuleType() == BaseModule.ModuleType.ADMIN)
                    .forEach(module -> setAdminGroupPermissions(adminGroupId, module));
        } catch (Exception e) {
            log.error("관리자 그룹 권한 설정 중 오류 발생", e);
            throw new RuntimeException("Failed to set admin group permissions", e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // 관리자 그룹에 기본 권한 부여
    //------------------------------------------------------------------------------------------------------------------
    private void setAdminGroupPermissions(String adminGroupId, BaseModule module) {
        try {
            Class<? extends ModulePermission> permissionEnum = module.getPermissionEnum();
            ModulePermission[] permissions = permissionEnum.getEnumConstants();

            log.info("모듈 [{}]의 관리자 권한을 부여합니다. 권한 개수: {}",
                    module.getModuleName(), permissions.length);

            Arrays.stream(permissions).forEach(permission -> {
                try {
                    // 권한이 이미 존재하는지 확인
                    if (!permissionManageService.existsGroupPermission(
                            adminGroupId,
                            module.getModuleId(),
                            null,
                            permission.getCode())) {

                        // 권한이 없는 경우에만 추가
                        permissionManageService.addGroupPermission(
                                adminGroupId,           // groupId
                                module.getModuleId(),   // moduleId
                                null,                   // instanceId
                                permission.getCode(),    // permissionCode
                                "SYSTEM"                // createdBy
                        );
                        log.debug("권한 추가됨 - groupId: {}, module: {}, permission: {}",
                                adminGroupId, module.getModuleId(), permission.getCode());
                    } else {
                        log.debug("권한 이미 존재함 - groupId: {}, module: {}, permission: {}",
                                adminGroupId, module.getModuleId(), permission.getCode());
                    }
                } catch (Exception e) {
                    log.warn("권한 처리 중 오류 발생 - groupId: {}, module: {}, permission: {}",
                            adminGroupId, module.getModuleId(), permission.getCode(), e);
                }
            });

            log.info("모듈 [{}]의 관리자 그룹 권한 설정이 완료되었습니다.", module.getModuleName());
        } catch (Exception e) {
            log.error("모듈 [{}]의 관리자 그룹 권한 설정 중 오류 발생", module.getModuleName(), e);
            throw new RuntimeException(
                    String.format("Failed to set admin group permissions for module %s", module.getModuleName()), e);
        }
    }
}
