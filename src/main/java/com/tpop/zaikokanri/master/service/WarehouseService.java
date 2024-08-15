package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Warehouse;

import java.util.List;
import java.util.Locale;

public interface WarehouseService {

    ApiResponse<Object> getWarehousePage(String warehouseCd , String warehouseName , Integer page , Integer limit) throws CommonException;

    ApiResponse<Object> getWarehouseById(Integer warehouseId, Locale locale);

    List<Warehouse> createWarehouse(List<Warehouse> warehouseList, String lang) throws CommonException;

    ApiResponse<Object> getWarehouseList();
}
