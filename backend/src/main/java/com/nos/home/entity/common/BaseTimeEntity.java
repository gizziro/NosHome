package com.nos.home.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 100, updatable = false)
    private String              createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime       createdAt;

    @LastModifiedBy
    @Column(name = "updated_by", length = 100)
    private String              updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime       updatedAt;
}
