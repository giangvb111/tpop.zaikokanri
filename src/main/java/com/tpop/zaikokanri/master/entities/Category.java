package com.tpop.zaikokanri.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "M_カテゴリ")
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID", nullable = false)
    private Integer id;

    @Column(name = "CATEGORY_CD", nullable = false)
    private String categoryCd;

    @Column(name = "MAJOR_CATEGORY")
    private String majorCategory;

    @Column(name = "MEDIUM_CATEGORY")
    private String mediumCategory;

    @Column(name = "SUB_CATEGORY")
    private String subCategory;

    public Category() {
    }

    public Category(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public Category(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy,
                    Integer id, String categoryCd, String majorCategory, String mediumCategory, String subCategory) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.categoryCd = categoryCd;
        this.majorCategory = majorCategory;
        this.mediumCategory = mediumCategory;
        this.subCategory = subCategory;
    }
}