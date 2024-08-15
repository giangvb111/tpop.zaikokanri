package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.ILocationDto;
import com.tpop.zaikokanri.master.entities.Location;
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
    public ApiResponse<Object> getLocationPage(String locationCd, String locationName, String warehouseName, Integer page, Integer limit) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page, limit);
        Page<ILocationDto> locationPage = locationRepository.getLocationsPage(locationCd, locationName, warehouseName, pageable);
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setMessage(null);
        response.setData(locationPage);
        return response;
    }

    /**
     * @param locationId
     * @param locale
     * @return
     */
    @Override
    public ApiResponse<Object> getLocationById(Integer locationId, Locale locale) {
        ApiResponse<Object> result = new ApiResponse<>();
        if (!Objects.isNull(locationId)) {
            ILocationDto optionalLocation = locationRepository.getLocationsByLocationId(locationId);
            if (optionalLocation == null) {
                result.setMessage(messageSource.getMessage(
                        MessageCode.CHECK_EXISTS, null, locale
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
     * @param locationList
     * @param locale
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class, Exception.class})
    public List<Location> createLocation(List<Location> locationList, Locale locale) throws CommonException {
        List<Location> createdLocation = new ArrayList<>();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(locationList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                locationList.forEach(l -> {
                    if (l.getLocationCd().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                "locationCd",
                                MessageCode.CHECK_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS, new Object[]{"locationCd"}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (l.getLocationName().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                "locationName",
                                MessageCode.CHECK_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS, new Object[]{"locationName"}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    if (Objects.isNull(l.getWarehouseId())) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                "warehouseId",
                                MessageCode.CHECK_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS, new Object[]{"warehouseId"}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                });

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.CHECK_EXISTS)
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
}
