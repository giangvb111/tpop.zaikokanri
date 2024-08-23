package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Warehouse;
import com.tpop.zaikokanri.master.service.impl.ScreenDisplaySettingServiceImpl;
import com.tpop.zaikokanri.master.service.impl.WarehouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/menu-setting")
public class ScreenDisplaySettingController {

    private final ScreenDisplaySettingServiceImpl settingService;

    @GetMapping(value = "/get-parent-screen-display-setting")
    public ResponseEntity<ApiResponse<Object>> getParentScreenDisplaySetting(@RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(settingService.getParentScreenDisplaySetting(lang));
    }

    @GetMapping(value = "/get-children-screen-display-setting")
    public ResponseEntity<ApiResponse<Object>> getWarehouse(@RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(settingService.getChildrenScreenDisplaySetting(lang));
    }

}
