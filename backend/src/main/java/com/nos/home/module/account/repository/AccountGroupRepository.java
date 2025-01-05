package com.nos.home.module.account.repository;

import com.nos.home.entity.account.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountGroupRepository extends JpaRepository<GroupEntity, Long>
{
   Optional<GroupEntity> findByGroupId(String groupId);
}
