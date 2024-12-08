package com.nos.home.entity.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class AccountEntity {
    public enum Provider {
        LOCAL, FACEBOOK, GOOGLE, APPLE
    }

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 Sequence 번호
    //------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long        userSeq;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 ID
    //------------------------------------------------------------------------------------------------------------------
    private String      userId;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 이메일
    //------------------------------------------------------------------------------------------------------------------
    private String      email;

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 패스워드
    //------------------------------------------------------------------------------------------------------------------
    @Column(length = 60)
    private String      password;

    private String      name;



    private String      phone;

    private boolean     useMfa;

    private boolean     enabled;

    //------------------------------------------------------------------------------------------------------------------
    // [로그인 실패 및 그에 따른 잠금 처리 여부]
    //------------------------------------------------------------------------------------------------------------------
    private int             failedLoginAttempts;

    private boolean         locked;

    private LocalDateTime   lockedDate;


    @Enumerated(EnumType.STRING)
    private Provider provider = Provider.LOCAL;

    @CreatedDate
    private LocalDateTime registrationDate;

}
