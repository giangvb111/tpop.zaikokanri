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
public class CustomerDto {

    @JsonProperty(value = "customerCd")
    private String customerCd;

    @JsonProperty(value = "customerName")
    private String customerName;

    @JsonProperty(value = "customerNameFormal")
    private String customerNameFormal;

    @JsonProperty(value = "supplierFlag")
    private String supplierFlag;

    @JsonProperty(value = "deliveryFlag")
    private String deliveryFlag;

    @JsonProperty(value = "requestFlag")
    private String requestFlag;

    @JsonProperty(value = "companyFlag")
    private String companyFlag;

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "postalCode")
    private String postalCode;

    @JsonProperty(value = "prefectures")
    private String prefectures;

    @JsonProperty(value = "municipalities")
    private String municipalities;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "building")
    private String building;

    @JsonProperty(value = "tel")
    private String tel;

    @JsonProperty(value = "fax")
    private String fax;

    @JsonProperty(value = "departmentName")
    private String departmentName;

    @JsonProperty(value = "picName")
    private String picName;

    @JsonProperty(value = "mailAddress")
    private String mailAddress;

    @JsonProperty(value = "homePage")
    private String homePage;

}
