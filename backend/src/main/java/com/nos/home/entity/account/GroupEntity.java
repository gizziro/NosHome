package com.nos.home.entity.account;

import jakarta.persistence.*;
import lombok.Getter;

//@Entity
@Getter
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupSeq;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean isEnabled = true;
}
