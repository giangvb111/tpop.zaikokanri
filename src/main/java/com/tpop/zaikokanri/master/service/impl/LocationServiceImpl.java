package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.FieldConstant;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.ILocationDto;
import com.tpop.zaikokanri.master.entities.Location;
import com.tpop.zaikokanri.master.entities.Warehouse;
import com.tpop.zaikokanri.master.repository.LocationRepository;
import com.tpop.zaikokanri.master.service.LocationService;
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
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final MessageSource messageSource;

    /**
     *
     * @param locationCd
     * @param locationName
     * @param warehouseName
     * @param page
     * @param limit
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getLocationPage(String locationCd, String locationName, String warehouseName, Integer page, Integer limit , String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale= Locale.forLanguageTag(lang);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ILocationDto> locationPage = locationRepository.getLocationsPage(locationCd, locationName, warehouseName, pageable);
        if (locationPage.getTotalElements() == 0) {
            response.setMessage(
                    messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    )
            );
        } else {
            response.setMessage(null);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(locationPage);
        return response;
    }

    /**
     * @param locationId
     * @param lang
     * @return
     */
    @Override
    public ApiResponse<Object> getLocationById(Integer locationId, String lang) {
        ApiResponse<Object> result = new ApiResponse<>();
        if (!Objects.isNull(locationId)) {
            Locale locale = Locale.forLanguageTag(lang);
            ILocationDto optionalLocation = locationRepository.getLocationsByLocationId(locationId);
            if (optionalLocation == null) {
                result.setMessage(messageSource.getMessage(
                        MessageCode.DATA_NOT_FOUND, null, locale
                ));
            } else {
                result.setMessage(null);
            }
            result.setStatus(ResponseStatusConst.SUCCESS);
            result.setData(optionalLocation);
        }
        return result;
    }

    /**
     *
     * @param locationCode
     * @return
     */
    @Override
    public Boolean getLocationByLocationCode(String locationCode) {
        Optional<Location> location = locationRepository.findLocationByLocationCd(locationCode);
        return location.isPresent();
    }

    /**
     * @param locationList
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class, Exception.class})
    public List<Location> createLocation(List<Location> locationList, String lang) throws CommonException {
        List<Location> createdLocation = new ArrayList<>();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            Locale locale = Locale.forLanguageTag(lang);
            if (!CollectionUtils.isEmpty(locationList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                locationList.forEach(l -> {
                    if (l.getLocationCd().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.LOCATION_CD,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.LOCATION_CD ,null , locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (l.getLocationName().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.LOCATION_NAME,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.LOCATION_NAME ,null , locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    if (Objects.isNull(l.getWarehouseId())) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_ID,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.WAREHOUSE_ID ,null , locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (Objects.isNull(l.getId()) && Boolean.TRUE.equals(getLocationByLocationCode(l.getLocationCd()))) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.LOCATION_NAME,
                                MessageCode.DATA_ALREADY_EXISTS ,
                                messageSource.getMessage(
                                        MessageCode.DATA_ALREADY_EXISTS , new Object[]{l.getLocationCd()}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                });

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.BAD_REQUEST)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                List<Location> list = new ArrayList<>();
                for (Location l : locationList) {
                    Location location = Location.builder()
                            .id(l.getId())
                            .locationCd(l.getLocationCd())
                            .locationName(l.getLocationName())
                            .warehouseId(l.getWarehouseId())
                            .createdAt(currentTime)
                            .createdBy("user")
                            .updatedAt(currentTime)
                            .updatedBy("user")
                            .build();
                    list.add(location);
                }
                createdLocation = locationRepository.saveAllAndFlush(list);
            }

        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return createdLocation;
    }

    /**
     *
     * @param locationIdList
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public ApiResponse<Object> deleteLocationByIdList(List<Integer> locationIdList, String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        if (!CollectionUtils.isEmpty(locationIdList)) {
            locationRepository.deleteAllById(locationIdList);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setMessage(
                messageSource.getMessage(MessageCode.DELETE_SUCCESS , null, locale)
        );
        response.setData(null);
        return response;
    }
}
