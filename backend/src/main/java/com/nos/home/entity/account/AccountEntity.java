package com.nos.home.entity.account;

import com.nos.home.user.account.dto.SignUpFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tb_user")
public class AccountEntity {
    public enum Provider {
        LOCAL, FACEBOOK, GOOGLE, APPLE
    }

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 ID
    //------------------------------------------------------------------------------------------------------------------
    @Id
    @Column(nullable = false, length = 50)
    private String          userId;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 이름
    //------------------------------------------------------------------------------------------------------------------
    private String          name;
    
    //------------------------------------------------------------------------------------------------------------------
    // 사용자 이메일
    //------------------------------------------------------------------------------------------------------------------
    private String          email;              // 사용자 이메일
    private boolean         emailVerified;      // 이메일 인증 여부
    private String          emailToken;         // 이메일 토큰 (회원 가입 시 이메일 인증 토큰)

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 패스워드
    //------------------------------------------------------------------------------------------------------------------
    @Column(length = 250)
    private String          password;           // 사용자 패스워드
    private LocalDateTime   passwordChangeDate; // 패스워드 변경 날짜

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 전화번호
    //------------------------------------------------------------------------------------------------------------------
    private String          phone;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 설명
    //------------------------------------------------------------------------------------------------------------------
    @Column(length = 500)
    private String          description;

    //------------------------------------------------------------------------------------------------------------------
    // MFA 사용 여부
    //------------------------------------------------------------------------------------------------------------------
    private boolean         useMfa;

    //------------------------------------------------------------------------------------------------------------------
    // 계정 활성화 여부
    //------------------------------------------------------------------------------------------------------------------
    private boolean         enabled;

    //------------------------------------------------------------------------------------------------------------------
    // [로그인 실패 및 그에 따른 잠금 처리 여부]
    //------------------------------------------------------------------------------------------------------------------
    private int             failedLoginAttempts;

    //------------------------------------------------------------------------------------------------------------------
    // 계정 잠금 여부
    //------------------------------------------------------------------------------------------------------------------
    private boolean         locked;

    //------------------------------------------------------------------------------------------------------------------
    // 계정 잠금 날짜
    //------------------------------------------------------------------------------------------------------------------
    private LocalDateTime   lockedDate;

    //------------------------------------------------------------------------------------------------------------------
    // 가입 방법
    //------------------------------------------------------------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    private Provider        provider = Provider.LOCAL;

    //------------------------------------------------------------------------------------------------------------------
    // 가입 날짜
    //------------------------------------------------------------------------------------------------------------------
    @CreatedDate
    private LocalDateTime   registrationDate;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자가 속한 그룹 목록
    //------------------------------------------------------------------------------------------------------------------
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserGroupMappingEntity> userGroupMappings = new HashSet<>();


    // Convenience method to add a group
    public void addGroup(GroupEntity group, String joinedBy) {
        // 이미 매핑이 존재하는지 확인
        boolean alreadyExists = this.userGroupMappings.stream()
                .anyMatch(mapping -> mapping.getGroup().getGroupId().equals(group.getGroupId()));

        if (!alreadyExists) {
            UserGroupMappingEntity mapping = new UserGroupMappingEntity();
            mapping.setUser(this);
            mapping.setGroup(group);
            mapping.setJoinedBy(joinedBy);
            this.userGroupMappings.add(mapping);
        }
    }

    // Convenience method to remove a group
    public void removeGroup(GroupEntity group) {
        UserGroupMappingEntity mapping = this.userGroupMappings.stream()
                .filter(m -> m.getGroup().equals(group))
                .findFirst()
                .orElse(null);
        if (mapping != null) {
            this.userGroupMappings.remove(mapping);
            group.getUserGroupMappings().remove(mapping);
            mapping.setUser(null);
            mapping.setGroup(null);
        }
    }

    //==================================================================================================================
    // [Static Methods]
    //==================================================================================================================

    public static AccountEntity getAdminEntity(String userId, String password, String phone)
    {
        AccountEntity account = new AccountEntity();
        account.setUserId(userId);
        account.setPassword(password);
        account.setPasswordChangeDate(LocalDateTime.now());     // 패스워드 변경 날짜
        account.setName("관리자");                               // 사용자 이름
        account.setPhone(phone);                                // 사용자 휴대폰번호
        account.setDescription("시스템 관리자");                  // 사용자 설명
        account.setEmail("");                                   // 사용자 이메일
        account.setEmailVerified(true);                         // 이메일 인증 여부
        account.setEmailToken(UUID.randomUUID().toString());    // 이메일 토큰 (회원 가입 시 이메일 인증 토큰)
        account.setUseMfa(false);                               // MFA 사용 여부
        account.setEnabled(true);                               // 계정 활성화 여부
        account.setFailedLoginAttempts(0);                      // [로그인 실패 및 그에 따른 잠금 처리 여부]
        account.setLocked(false);                               // 계정 잠금 여부
        account.setLockedDate(null);                            // 계정 잠금 날짜
        account.setProvider(Provider.LOCAL);                    // 가입 방법
        return account;
    }

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 정보를 업데이트 한다.
    //------------------------------------------------------------------------------------------------------------------
    public static AccountEntity of(SignUpFormDto creatDto)
    {
        AccountEntity account = new AccountEntity();
        account.setUserId(creatDto.getUserId());                // 사용자 ID
        account.setPassword(creatDto.getPassword());            // 사용자 패스워드
        account.setPasswordChangeDate(LocalDateTime.now());     // 패스워드 변경 날짜
        account.setName(creatDto.getName());                    // 사용자 이름
        account.setPhone(creatDto.getPhone());                  // 사용자 휴대폰번호
        account.setDescription("");                             // 사용자 설명
        account.setEmail(creatDto.getEmail());                  // 사용자 이메일
        account.setEmailVerified(false);                        // 이메일 인증 여부
        account.setEmailToken(UUID.randomUUID().toString());    // 이메일 토큰 (회원 가입 시 이메일 인증 토큰)
        account.setUseMfa(false);                               // MFA 사용 여부
        account.setEnabled(true);                               // 계정 활성화 여부
        account.setFailedLoginAttempts(0);                      // [로그인 실패 및 그에 따른 잠금 처리 여부]
        account.setLocked(false);                               // 계정 잠금 여부
        account.setLockedDate(null);                            // 계정 잠금 날짜
        account.setProvider(Provider.LOCAL);                    // 가입 방법

        return account;
    }
}
