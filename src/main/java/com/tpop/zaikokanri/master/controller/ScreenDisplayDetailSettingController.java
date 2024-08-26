package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.service.impl.ScreenDisplayDetailSettingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/screen-detail-setting")
public class ScreenDisplayDetailSettingController {

    private final ScreenDisplayDetailSettingServiceImpl settingService;

    @GetMapping(value = "/get-screen-by-function-code")
    public ResponseEntity<ApiResponse<Object>> getScreenByFunctionCode(@RequestParam(value = "functionCode") String functionCode ,
                                                                       @RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(settingService.getScreenDetailSettingByFunctionCode(functionCode ,lang));
    }

}
