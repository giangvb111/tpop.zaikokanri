package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Warehouse;

import java.util.List;

public interface WarehouseService {

    ApiResponse<Object> getWarehousePage(String warehouseCd , String warehouseName , Integer page , Integer limit ,String lang) throws CommonException;

    ApiResponse<Object> getWarehouseById(Integer warehouseId, String lang);

    List<Warehouse> createWarehouse(List<Warehouse> warehouseList, String lang) throws CommonException;

    ApiResponse<Object> getWarehouseList();

    Boolean getWarehouseByWarehouseCode(String warehouseCode);

    Boolean getWarehouseByWarehouseName(String warehouseName);

    ApiResponse<Object> deleteWarehouseByIdList(List<Integer> warehouseIdList , String lang) throws CommonException;

}
