package com.tpop.zaikokanri.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"S_機能\"")
public class Function extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FUNCTION_ID", nullable = false)
    private Integer id;

    @Column(name = "FUNCTION_SECTION", nullable = false, length = 1)
    private String functionSection;

    @Column(name = "FUNCTION_NAME", nullable = false)
    private String functionName;

    @Column(name = "FUNCTION_CD", nullable = false)
    private String functionCd;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "URL")
    private String url;

    public Function() {
    }

    public Function(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public Function(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy,
                    Integer id, String functionSection, String functionName, String functionCd, Integer displayOrder, String url) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.functionSection = functionSection;
        this.functionName = functionName;
        this.functionCd = functionCd;
        this.displayOrder = displayOrder;
        this.url = url;
    }
}