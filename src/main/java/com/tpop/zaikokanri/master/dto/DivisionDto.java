package com.tpop.zaikokanri.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpop.zaikokanri.annotation.MapFieldToColumn;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DivisionDto {

    @MapFieldToColumn(value = "divisionId")
    @JsonProperty(value = "DIVISION_ID")
    private Integer id;

    @MapFieldToColumn(value = "divisionCd")
    @JsonProperty(value = "DIVISION_CD")
    private String divisionCd;

    @MapFieldToColumn(value = "divisionName")
    @JsonProperty(value = "DIVISION_NAME")
    private String divisionName;

    private List<Integer> warehouseIdList;
}