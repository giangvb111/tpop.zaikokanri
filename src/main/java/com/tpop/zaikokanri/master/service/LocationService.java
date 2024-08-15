package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Location;

import java.util.List;

public interface LocationService {

    ApiResponse<Object> getLocationPage(String locationCd,String locationName, String warehouseName, Integer page , Integer limit ,String lang) throws CommonException;

    ApiResponse<Object> getLocationById(Integer locationId, String lang);

    List<Location> createLocation(List<Location> locationList, String lang) throws CommonException;

    Boolean getLocationByLocationCode(String locationCode);
}
