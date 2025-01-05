package com.nos.home.entity.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user_group_mapping",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "group_id"})})
@Getter
@Setter
public class UserGroupMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long            seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AccountEntity   user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity     group;

    @Column(nullable = false, updatable = false)
    private LocalDateTime   joinTime = LocalDateTime.now();

    @Column(nullable = false, length = 100)
    private String          joinedBy;
}
