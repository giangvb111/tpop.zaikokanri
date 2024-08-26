package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;

public interface ScreenDisplayDetailSettingService {

    ApiResponse<Object> getScreenDetailSettingByFunctionCode(String functionCode, String lang);
}
