package com.tpop.zaikokanri.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IDivisionDto {

    @JsonProperty(value = "DIVISION_ID")
    Integer getDivisionId();

    @JsonProperty(value = "DIVISION_CD")
    String getDivisionCd();

    @JsonProperty(value = "DIVISION_NAME")
    String getDivisionName();

    @JsonProperty(value = "WAREHOUSE_CD")
    String getWarehouseCd();

    @JsonProperty(value = "WAREHOUSE_NAME")
    String getWarehouseName();

}
