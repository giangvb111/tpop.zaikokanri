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
@Table(name = "M_部門")
public class Division extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIVISION_ID", nullable = false)
    private Integer id;

    @Column(name = "DIVISION_CD", length = 10)
    private String divisionCd;

    @Column(name = "DIVISION_NAME", nullable = false)
    private String divisionName;

    public Division() {
    }

    public Division(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public Division(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, Integer id, String divisionCd, String divisionName) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.divisionCd = divisionCd;
        this.divisionName = divisionName;
    }
}