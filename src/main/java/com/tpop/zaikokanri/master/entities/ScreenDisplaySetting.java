package com.tpop.zaikokanri.master.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"S_画面表示設定\"")
public class ScreenDisplaySetting extends BaseEntity{
    @Id
    @Column(name = "DISP_SETTING_ID", nullable = false)
    private Integer id;

    @Column(name = "FUNCTION_ID", nullable = false)
    private Integer functionId;

    @Column(name = "TABLE_ID", nullable = false)
    private Integer tableId;

    @Column(name = "DISP_SECTION")
    private Integer dispSection;

    public ScreenDisplaySetting() {
    }

    public ScreenDisplaySetting(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public ScreenDisplaySetting(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy,
                                Integer id, Integer functionId, Integer tableId, Integer dispSection) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.functionId = functionId;
        this.tableId = tableId;
        this.dispSection = dispSection;
    }
}