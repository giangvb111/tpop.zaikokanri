package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.FieldConstant;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.IWarehouseDto;
import com.tpop.zaikokanri.master.entities.Warehouse;
import com.tpop.zaikokanri.master.repository.WarehouseRepository;
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
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    private final LocationServiceImpl locationService;

    private final DivisionServiceImpl divisionService;

    private final MessageSource messageSource;

    /**
     * Get Warehouse by Warehouse Code
     * @param warehouseCode
     * @return True if value exists
     */
    @Override
    public Boolean getWarehouseByWarehouseCode(String warehouseCode) {
        Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByWarehouseCd(warehouseCode);
        return warehouse.isPresent();
    }

    /**
     * Get Warehouse by Warehouse Name
     * @param warehouseName
     * @return True if value exists
     */
    @Override
    public Boolean getWarehouseByWarehouseName(String warehouseName) {
        Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByWarehouseName(warehouseName);
        return warehouse.isPresent();
    }

    /**
     * Create Warehouse
     * @param warehouseList
     * @param lang
     * @return Created Warehouse List
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class, Exception.class})
    public List<Warehouse> createWarehouse(List<Warehouse> warehouseList, String lang) throws CommonException {
        List<Warehouse> createdWarehouse = new ArrayList<>();
        try {
            LocalDateTime current = LocalDateTime.now();
            Locale locale = Locale.forLanguageTag(lang);
            if (!CollectionUtils.isEmpty(warehouseList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                /* check Warehouse List */
                warehouseList.forEach(w -> {
                    if (w.getWarehouseCd().isBlank()) {
                        /* Case Warehouse Code Blank  */
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_CODE,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.WAREHOUSE_CODE, null, locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    /* Case Warehouse Name Blank  */
                    if (w.getWarehouseName().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_NAME,
                                MessageCode.DATA_ALREADY_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.WAREHOUSE_NAME, null, locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    /* Case Customer Code Already Exists In Database  */
                    if (Objects.isNull(w.getId()) && Boolean.TRUE.equals(getWarehouseByWarehouseCode(w.getWarehouseCd()))) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_CODE,
                                MessageCode.DATA_ALREADY_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.DATA_ALREADY_EXISTS, new Object[]{w.getWarehouseCd()}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    /* Case Customer Name Already Exists In Database  */
                    if (Objects.isNull(w.getId()) && Boolean.TRUE.equals(getWarehouseByWarehouseName(w.getWarehouseName()))) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_NAME,
                                MessageCode.DATA_ALREADY_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.DATA_ALREADY_EXISTS, new Object[]{w.getWarehouseName()}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (!Objects.isNull(w.getId())) {
                        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(w.getId());
                        if (optionalWarehouse.isPresent()
                                && !optionalWarehouse.get().getWarehouseCd().equals(w.getWarehouseCd())
                                && Boolean.TRUE.equals(getWarehouseByWarehouseCode(w.getWarehouseCd()))) {
                            APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                    i.intValue(),
                                    FieldConstant.WAREHOUSE_CODE,
                                    MessageCode.DATA_ALREADY_EXISTS,
                                    messageSource.getMessage(
                                            MessageCode.DATA_ALREADY_EXISTS, new Object[]{w.getWarehouseCd()}, locale
                                    )
                            );
                            errorDetails.add(apiErrorDetail);
                        }

                        if (optionalWarehouse.isPresent()
                                && !optionalWarehouse.get().getWarehouseName().equals(w.getWarehouseName())
                                && Boolean.TRUE.equals(getWarehouseByWarehouseName(w.getWarehouseName()))) {
                            APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                    i.intValue(),
                                    FieldConstant.WAREHOUSE_NAME,
                                    MessageCode.DATA_ALREADY_EXISTS,
                                    messageSource.getMessage(
                                            MessageCode.DATA_ALREADY_EXISTS, new Object[]{w.getWarehouseName()}, locale
                                    )
                            );
                            errorDetails.add(apiErrorDetail);
                        }
                    }
                });

                /* add each element to the list and then execute save all  */
                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.BAD_REQUEST)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                /* add each element to the list and then execute save all  */
                List<Warehouse> list = new ArrayList<>();
                for (Warehouse w : warehouseList) {
                    Warehouse warehouse = Warehouse.builder()
                            .id(w.getId())
                            .warehouseCd(w.getWarehouseCd())
                            .warehouseName(w.getWarehouseName())
                            .createdAt(current)
                            .createdBy("user")
                            .updatedAt(current)
                            .updatedBy("user")
                            .build();
                    list.add(warehouse);
                }
                createdWarehouse = warehouseRepository.saveAllAndFlush(list);
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

        return createdWarehouse;
    }

    /**
     * @return Warehouse List
     */
    @Override
    public ApiResponse<Object> getWarehouseList() {
        ApiResponse<Object> response = new ApiResponse<>();
        List<Warehouse> warehouseList;
        warehouseList = warehouseRepository.findAll();
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setMessage(null);
        response.setData(warehouseList);
        return response;
    }


    /**
     *
     * @param warehouseId
     * @param lang
     * @return Warehouse
     */
    @Override
    public ApiResponse<Object> getWarehouseById(Integer warehouseId, String lang) {
        ApiResponse<Object> result = new ApiResponse<>();
        if (!Objects.isNull(warehouseId)) {
            Locale locale = Locale.forLanguageTag(lang);
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
            if (optionalWarehouse.isEmpty()) {
                result.setMessage(messageSource.getMessage(
                        MessageCode.DATA_NOT_FOUND, null, locale
                ));
            } else {
                result.setMessage(null);
            }
            result.setStatus(ResponseStatusConst.SUCCESS);
            result.setData(optionalWarehouse);
        }
        return result;
    }

    /**
     * @param warehouseCd
     * @param warehouseName
     * @param page
     * @param limit
     * @return Warehouse Page
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getWarehousePage(String warehouseCd, String warehouseName, Integer page, Integer limit, String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        Pageable pageable = PageRequest.of(page, limit);
        Page<Warehouse> warehousePage = warehouseRepository.findByWarehouseCdContainingAndWarehouseNameContaining(warehouseCd, warehouseName, pageable);
        if (warehousePage.getTotalElements() == 0) {
            /* case of no data */
            response.setMessage(
                    messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    )
            );
        } else {
            /* case of data */
            response.setMessage(null);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(warehousePage);
        return response;
    }

    /**
     * Delete Warehouse By Warehouse Id List
     * @param warehouseIdList
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class, Exception.class})
    public ApiResponse<Object> deleteWarehouseByIdList(List<Integer> warehouseIdList, String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            Locale locale = Locale.forLanguageTag(lang);
            if (!CollectionUtils.isEmpty(warehouseIdList)) {
                /* check Warehouse used in Location */
                List<IWarehouseDto> warehouseList = locationService.findWarehouseIdListUsedInLocation(warehouseIdList);
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                if (!CollectionUtils.isEmpty(warehouseList)) {
                    warehouseList.forEach(s -> {
                        if (warehouseIdList.contains(s.getId())) {
                            APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                    i.intValue(),
                                    FieldConstant.WAREHOUSE_NAME,
                                    MessageCode.IN_USING,
                                    messageSource.getMessage(MessageCode.IN_USING, new Object[]{s.getWarehouseName(),
                                            messageSource.getMessage(FieldConstant.LOCATION, null, locale)}, locale)
                            );
                            errorDetails.add(apiErrorDetail);
                        }
                    });
                }

                /* check Warehouse used in Division */
                List<IWarehouseDto> warehouseDivisionList = divisionService.findWarehouseIdListUsedInDivision(warehouseIdList);
                if (!CollectionUtils.isEmpty(warehouseDivisionList)) {
                    warehouseDivisionList.forEach(s -> {
                        if (warehouseIdList.contains(s.getId())) {
                            APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                    i.intValue(),
                                    FieldConstant.WAREHOUSE_NAME,
                                    MessageCode.IN_USING,
                                    messageSource.getMessage(MessageCode.IN_USING, new Object[]{s.getWarehouseName(),
                                            messageSource.getMessage(FieldConstant.DIVISION, null, locale)}, locale)
                            );
                            errorDetails.add(apiErrorDetail);
                        }
                    });
                }

                /* If errorDetails is not empty, an exception will be thrown.  */
                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.BAD_REQUEST)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                /* If errorDetails is empty, Delete All By Warehouse id List.  */
                warehouseRepository.deleteAllById(warehouseIdList);
                response.setStatus(ResponseStatusConst.SUCCESS);
                response.setMessage(
                        messageSource.getMessage(MessageCode.DELETE_SUCCESS, null, locale)
                );
                response.setData(null);

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
        return response;
    }


}
