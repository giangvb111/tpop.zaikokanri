package com.tpop.zaikokanri.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"S_画面表示詳細設定\"")
public class ScreenDisplayDetailSetting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISP_SET_DETAIL_ID", nullable = false)
    private Integer id;

    @Column(name = "DISP_SETTING_ID", nullable = false)
    private Integer dispSettingId;

    @Column(name = "TABLE_ID", nullable = false)
    private Integer tableId;

    @Column(name = "DISP_DETAIL_SECTION")
    private Integer dispDetailSection;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "DISP_ORDER")
    private Integer dispOrder;

    @Column(name = "REQUIRE_FLG")
    private Integer requireFlg;

    @Column(name = "DISP_FLAG")
    private Integer dispFlag;

    public ScreenDisplayDetailSetting() {
    }

    public ScreenDisplayDetailSetting(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public ScreenDisplayDetailSetting(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy,
                                      Integer id, Integer dispSettingId, Integer tableId, Integer dispDetailSection, String itemName, Integer dispOrder, Integer requireFlg, Integer dispFlag) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.dispSettingId = dispSettingId;
        this.tableId = tableId;
        this.dispDetailSection = dispDetailSection;
        this.itemName = itemName;
        this.dispOrder = dispOrder;
        this.requireFlg = requireFlg;
        this.dispFlag = dispFlag;
    }
}