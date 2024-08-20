package com.tpop.zaikokanri.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpop.zaikokanri.master.entities.WarehouseDivision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DivisionDto {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "divisionCd")
    private String divisionCd;

    @JsonProperty(value = "divisionName")
    private String divisionName;

    @JsonProperty(value = "divisionWarehouseList")
    private List<WarehouseDivision> divisionWarehouseList;
}