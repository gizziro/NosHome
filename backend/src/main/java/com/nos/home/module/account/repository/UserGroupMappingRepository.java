package com.nos.home.module.account.repository;

import com.nos.home.entity.account.AccountEntity;
import com.nos.home.entity.account.GroupEntity;
import com.nos.home.entity.account.UserGroupMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Long> {
    // Find all mappings for a specific user
    List<UserGroupMappingEntity> findByUser(AccountEntity user);

    // Find all mappings for a specific group
    List<UserGroupMappingEntity> findByGroup(GroupEntity group);

    // Optional: Find a specific mapping
    Optional<UserGroupMappingEntity> findByUserAndGroup(AccountEntity user, GroupEntity group);
}