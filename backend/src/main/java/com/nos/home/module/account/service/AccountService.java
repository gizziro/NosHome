package com.nos.home.module.account.service;

import com.nos.home.entity.account.AccountEntity;
import com.nos.home.entity.account.GroupEntity;
import com.nos.home.entity.account.UserGroupMappingEntity;
import com.nos.home.module.account.repository.AccountGroupRepository;
import com.nos.home.module.account.repository.AccountRepository;
import com.nos.home.module.account.repository.UserGroupMappingRepository;
import com.nos.home.user.account.dto.SignUpFormDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    private final AccountRepository             accountRepository;
    private final AccountGroupRepository        groupRepository;
    private final PasswordEncoder               passwordEncoder;
    private final UserGroupMappingRepository    mappingRepository;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 ID 중복 체크
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public boolean checkId(String userId) {
        Optional<AccountEntity> userEntity = accountRepository.findByUserId(userId);
        return userEntity.isPresent();
    }

    @Transactional
    public boolean createUser(SignUpFormDto createDto){
        //--------------------------------------------------------------------------------------------------------------
        // 비밀번호 단방향 암호촤 수행
        //--------------------------------------------------------------------------------------------------------------
        createDto.setPassword(passwordEncoder.encode(createDto.getPassword()));
        AccountEntity entity = AccountEntity.of(createDto);
        accountRepository.save(entity);
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    // 관리자 계정 생성
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public void createAdminAccount(String adminUserId, String adminPassword, String adminPhone)
    {
        String encodedPassword = passwordEncoder.encode(adminPassword);
        AccountEntity.getAdminEntity(adminUserId, encodedPassword, adminPhone);
        accountRepository.save(AccountEntity.getAdminEntity(adminUserId, encodedPassword, adminPhone));
    }

    //==================================================================================================================
    // 사용자 그룹 관련 처리
    //==================================================================================================================

    //------------------------------------------------------------------------------------------------------------------
    // 사용자의 그룹 정보 조회
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public List<GroupEntity> getUserGroups(String userId) {
        AccountEntity user = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        return user.getUserGroupMappings().stream()
                .map(UserGroupMappingEntity::getGroup)
                .collect(Collectors.toList());
    }

    //------------------------------------------------------------------------------------------------------------------
    // 사용자가 속한 그룹인지 확인
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public boolean isUserInGroup(String userId, String groupId) {
        AccountEntity user = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        GroupEntity group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found: " + groupId));

        return mappingRepository.findByUserAndGroup(user, group).isPresent();
    }

    //------------------------------------------------------------------------------------------------------------------
    // 그룹에 사용자 정보 추가
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public void addUserToGroup(String userId, String groupId, String joinedBy)
    {
        //--------------------------------------------------------------------------------------------------------------
        // 사용자 조회
        //--------------------------------------------------------------------------------------------------------------
        AccountEntity   user    = accountRepository.findByUserId(userId)
                                    .orElseThrow(() -> new RuntimeException("User not found"));
        GroupEntity     group   = groupRepository.findByGroupId(groupId)
                                    .orElseThrow(() -> new RuntimeException("Group not found"));

        // 이미 그룹에 속해있는지 확인
        boolean alreadyExists = mappingRepository.findByUserAndGroup(user, group).isPresent();
        if (!alreadyExists)
        {
            user.addGroup(group, joinedBy);
            accountRepository.save(user);
        }
    }

    @Transactional
    public void removeUserFromGroup(String userId, String groupId) {
        AccountEntity user = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        GroupEntity group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found: " + groupId));

        user.removeGroup(group);
        accountRepository.save(user); // Cascades to mapping
    }
}

