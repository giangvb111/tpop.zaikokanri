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

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "divisionCd")
    private String divisionCd;

    @JsonProperty(value = "divisionName")
    private String divisionName;

    @JsonProperty(value = "warehouseId")
    private Integer warehouseId;
}