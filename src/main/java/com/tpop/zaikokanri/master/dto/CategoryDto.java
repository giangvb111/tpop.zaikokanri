package com.tpop.zaikokanri.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    @JsonProperty(value = "CATEGORY_CD")
    private String categoryCd;

    @JsonProperty(value = "MAJOR_CATEGORY")
    private String majorCategory;

    @JsonProperty(value = "MEDIUM_CATEGORY")
    private String mediumCategory;

    @JsonProperty(value = "SUB_CATEGORY")
    private String subCategory;
}
