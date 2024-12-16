package com.nos.home.entity.account;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "TB_PERMISSION")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long        permissionSeq;

    @Column(nullable = false, length = 100)
    private String      name;

    @Column(length = 500)
    private String      description;
}
