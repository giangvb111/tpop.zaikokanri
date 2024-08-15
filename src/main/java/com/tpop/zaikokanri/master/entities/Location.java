package com.tpop.zaikokanri.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"M_棚番\"")
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_ID", nullable = false)
    private Integer id;

    @Column(name = "LOCATION_CD", nullable = false)
    private String locationCd;

    @Column(name = "LOCATION_NAME", nullable = false)
    private String locationName;

    @Column(name = "WAREHOUSE_ID", nullable = false)
    private Integer warehouseId;

    public Location() {
    }

    public Location(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public Location(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy,
                    Integer id, String locationCd,String locationName , Integer warehouseId) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.locationCd = locationCd;
        this.locationName = locationName;
        this.warehouseId = warehouseId;
    }
}