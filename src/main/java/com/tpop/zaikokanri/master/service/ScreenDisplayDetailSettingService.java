package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;

public interface ScreenDisplayDetailSettingService {

    ApiResponse<Object> getParentScreenDisplaySetting(String lang) throws CommonException;

    ApiResponse<Object> getChildrenScreenDisplaySetting(String lang) throws CommonException;

    ApiResponse<Object> getScreenDetailSettingByFunctionCode(String functionCode,String displaySection, String lang);
}
