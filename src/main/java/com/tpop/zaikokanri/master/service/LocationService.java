package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Location;

import java.util.List;
import java.util.Locale;

public interface LocationService {

    ApiResponse<Object> getLocationPage(String locationCd,String locationName, String warehouseName, Integer page , Integer limit) throws CommonException;

    ApiResponse<Object> getLocationById(Integer locationId, Locale locale);

    List<Location> createLocation(List<Location> locationList, Locale locale) throws CommonException;
}
