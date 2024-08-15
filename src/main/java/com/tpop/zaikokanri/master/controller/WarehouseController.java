package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Warehouse;
import com.tpop.zaikokanri.master.service.impl.WarehouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/warehouse")
public class WarehouseController {

    private final WarehouseServiceImpl warehouseService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<Warehouse>> createWarehouse(@RequestBody List<Warehouse> categoryList, @RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(warehouseService.createWarehouse(categoryList, lang));
    }

    @GetMapping(value = "/get-warehouse-by-id")
    public ResponseEntity<ApiResponse<Object>> getWarehouseById(@RequestParam(name = "id") Integer warehouseId, Locale locale) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(warehouseId, locale));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getWarehouse(@RequestParam(value = "warehouseCd") String warehouseCd,
                                                            @RequestParam(value = "warehouseName") String warehouseName,
                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "limit", defaultValue = "100") Integer limit) throws CommonException {
        return ResponseEntity.ok(warehouseService.getWarehousePage(warehouseCd, warehouseName, page, limit));
    }

    @GetMapping(value = "/get-all-list")
    public ResponseEntity<ApiResponse<Object>> getWarehouseList() {
        return ResponseEntity.ok(warehouseService.getWarehouseList());
    }
}
