package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.IScreenDetailDisplaySettingDto;
import com.tpop.zaikokanri.master.dto.IScreenDisplaySettingDto;
import com.tpop.zaikokanri.master.repository.ScreenDisplayDetailSettingRepository;
import com.tpop.zaikokanri.master.repository.ScreenDisplaySettingRepository;
import com.tpop.zaikokanri.master.service.ScreenDisplayDetailSettingService;
import com.tpop.zaikokanri.master.service.ScreenDisplaySettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScreenDisplayDetailSettingServiceImpl implements ScreenDisplayDetailSettingService {

    private final ScreenDisplayDetailSettingRepository settingRepository;

    private final MessageSource messageSource;

    /**
     *
     * @param functionCode
     * @return
     */
    public List<Integer> getDisplaySectionByFunctionCode (String functionCode) {
        return settingRepository.getDisplaySectionByFunctionCode(functionCode);
    }

    /**
     *
     * @param functionCode
     * @param lang
     * @return Screen Detail Setting By Function Code
     */
    @Override
    public ApiResponse<Object> getScreenDetailSettingByFunctionCode(String functionCode, String lang) {
        ApiResponse<Object> response = new ApiResponse<>();
        List<Integer> displaySection = getDisplaySectionByFunctionCode(functionCode);
        List<IScreenDetailDisplaySettingDto> settingDto = settingRepository.getScreenDetailSettingByFunctionCode(functionCode,displaySection);
        if (!Objects.isNull(settingDto)) {
            response.setData(settingDto);
        } else {
            response.setData(null);
        }
        response.setMessage(null);
        response.setStatus(ResponseStatusConst.SUCCESS);
        return response;
    }

}
