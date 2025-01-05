package com.nos.home.entity.account;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_group")
@Getter
public class GroupEntity {

    @Id
    @Column(nullable = false, length = 100)
    private String          groupId;

    @Column(nullable = false, length = 100)
    private String          name;

    @Column(length = 500)
    private String          description;

    private Boolean         systemGroup     = false;

    @Column(nullable = false)
    private Boolean         enable       = true;

    @CreatedDate
    private LocalDateTime   createTime;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserGroupMappingEntity> userGroupMappings = new HashSet<>();


    public static GroupEntity of(String groupId, String name, String description, boolean systemGroup, boolean enable) {
        GroupEntity group   = new GroupEntity();
        group.groupId       = groupId;
        group.name          = name;
        group.description   = description;
        group.systemGroup   = systemGroup;
        group.enable        = enable;
        group.createTime    = LocalDateTime.now();
        return group;
    }
}
