package com.tpop.zaikokanri.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ICategoryDto {

    @JsonProperty(value = "CATEGORY_ID")
    Integer getId();

    @JsonProperty(value = "CATEGORY_CD")
    String getCategoryCd();

    @JsonProperty(value = "MAJOR_CATEGORY")
    String getMajorCategory();

    @JsonProperty(value = "MEDIUM_CATEGORY")
    String getMediumCategory();

    @JsonProperty(value = "SUB_CATEGORY")
    String getSubCategory();

}
