package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Warehouse;

import java.util.List;

public interface ScreenDisplaySettingService {

    ApiResponse<Object> getParentScreenDisplaySetting(String lang) throws CommonException;

    ApiResponse<Object> getChildrenScreenDisplaySetting(String lang) throws CommonException;
}
