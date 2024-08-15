package com.tpop.zaikokanri.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"M_部門_倉庫\"")
public class WarehouseDivision extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIVI_WAREHOUSE_ID", nullable = false)
    private Integer diviWarehouseId;

    @Column(name = "DIVISION_ID", nullable = false)
    private Integer divisionId;

    @Column(name = "WAREHOUSE_ID", nullable = false)
    private Integer warehouseId;

    public WarehouseDivision() {
    }

    public WarehouseDivision(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public WarehouseDivision(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt,
                             String updatedBy, Integer diviWarehouseId, Integer divisionId, Integer warehouseId) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.diviWarehouseId = diviWarehouseId;
        this.divisionId = divisionId;
        this.warehouseId = warehouseId;
    }
}