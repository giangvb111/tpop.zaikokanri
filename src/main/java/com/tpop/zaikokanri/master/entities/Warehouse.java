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
@Table(name = "\"M_倉庫\"")
public class Warehouse extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WAREHOUSE_ID", nullable = false)
    private Integer id;

    @Column(name = "WAREHOUSE_CD", nullable = false)
    private String warehouseCd;

    @Column(name = "WAREHOUSE_NAME", nullable = false)
    private String warehouseName;

    public Warehouse() {
    }

    public Warehouse(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public Warehouse(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy,
                     Integer id, String warehouseCd, String warehouseName) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.warehouseCd = warehouseCd;
        this.warehouseName = warehouseName;
    }
}