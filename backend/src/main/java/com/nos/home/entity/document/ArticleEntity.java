package com.nos.home.entity.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "TB_ARTICLE")
public class ArticleEntity
{
    @Id
    private Long id;

    @Setter @Column(nullable = false)
    private String title;          // 제목
    @Setter @Column(nullable = false)
    private String content;        // 내용


    //------------------------------------------------------------------------------------------------------------------
    //
    //------------------------------------------------------------------------------------------------------------------
    @CreatedDate        private LocalDateTime       createdDate;            // 작성 시간
    @CreatedBy          private String              createdBy;              // 작성자
    @LastModifiedDate   private LocalDateTime       lastModifiedDate;       // 수정 시간
    @LastModifiedBy     private String              lastModifiedBy;         // 수정자


    protected ArticleEntity() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity that = (ArticleEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
