package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.FieldConstant;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.IScreenDisplaySettingDto;
import com.tpop.zaikokanri.master.dto.ScreenDisplaySettingDto;
import com.tpop.zaikokanri.master.entities.Warehouse;
import com.tpop.zaikokanri.master.repository.ScreenDisplaySettingRepository;
import com.tpop.zaikokanri.master.repository.WarehouseRepository;
import com.tpop.zaikokanri.master.service.ScreenDisplaySettingService;
import com.tpop.zaikokanri.master.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ScreenDisplaySettingServiceImpl implements ScreenDisplaySettingService {

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
        List<Integer> parentIdList = list.stream().map(IScreenDisplaySettingDto::getParentId).toList();
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

}
