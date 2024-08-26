package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.IScreenDisplaySettingDto;
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

@Service
@RequiredArgsConstructor
public class ScreenDisplayDetailSettingServiceImpl implements ScreenDisplayDetailSettingService {

    private final ScreenDisplaySettingRepository screenDisplaySettingRepository;

    private final MessageSource messageSource;

    /**
     *
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getParentScreenDisplaySetting(String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        List<IScreenDisplaySettingDto> screenDisplaySettingDtos = getScreenDisplaySetting(lang);
        if (!CollectionUtils.isEmpty(screenDisplaySettingDtos)) {
            response.setMessage(null);
        } else {
            response.setMessage(messageSource.getMessage(MessageCode.DATA_NOT_FOUND , null , locale));
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(screenDisplaySettingDtos);
        return response;
    }

    /**
     *
     * @param lang
     * @return
     */
    public List<IScreenDisplaySettingDto> getScreenDisplaySetting (String lang) {
        return screenDisplaySettingRepository.getParentScreenDisplaySetting(lang);
    }

    /**
     *
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getChildrenScreenDisplaySetting(String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        List<IScreenDisplaySettingDto> list = getScreenDisplaySetting(lang);
        List<Integer> parentIdList = new ArrayList<>(list.stream()
                .map(IScreenDisplaySettingDto::getFunctionId)
                .toList());
        List<IScreenDisplaySettingDto> childrenScreenList = screenDisplaySettingRepository.getChildrenScreenDisplaySetting(parentIdList);
        if (!CollectionUtils.isEmpty(childrenScreenList)) {
            response.setMessage(null);
        } else {
            response.setMessage(messageSource.getMessage(MessageCode.DATA_NOT_FOUND , null ,locale ));
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(childrenScreenList);
        return response;
    }

    /**
     *
     * @param functionCode
     * @param lang
     * @return Screen Detail Setting By Function Code
     */
    @Override
    public ApiResponse<Object> getScreenDetailSettingByFunctionCode(String functionCode,String displaySection, String lang) {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);

        return response;
    }

}
