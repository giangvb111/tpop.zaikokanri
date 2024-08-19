package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.FieldConstant;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
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

    private final MessageSource messageSource;

    /**
     *
     * @param warehouseCode
     * @return
     */
    @Override
    public Boolean getWarehouseByWarehouseCode(String warehouseCode) {
        Optional<Warehouse> warehouse = warehouseRepository.findWarehouseByWarehouseCd(warehouseCode);
        return warehouse.isPresent();
    }

    /**
     *
     * @param warehouseList
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public List<Warehouse> createWarehouse(List<Warehouse> warehouseList, String lang) throws CommonException {
        List<Warehouse> createdWarehouse  = new ArrayList<>();
        try {
            LocalDateTime current  = LocalDateTime.now();
            Locale locale = Locale.forLanguageTag(lang);
            if (!CollectionUtils.isEmpty(warehouseList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                warehouseList.forEach(w ->{
                    if (w.getWarehouseCd().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_CODE,
                                MessageCode.NOT_BLANK ,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK , new Object[]{
                                                messageSource.getMessage(FieldConstant.WAREHOUSE_CODE , null , locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (w.getWarehouseName().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_NAME,
                                MessageCode.DATA_ALREADY_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK , new Object[]{
                                                messageSource.getMessage(FieldConstant.WAREHOUSE_NAME , null , locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (Objects.isNull(w.getId())) {
                        if (Boolean.TRUE.equals(getWarehouseByWarehouseCode(w.getWarehouseCd()))) {
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
                    } else {
                        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(w.getId());

                        if (
                                optionalWarehouse.get().getWarehouseCd().equals(w.getWarehouseCd())
                                        && optionalWarehouse.get().getWarehouseName().equals(w.getWarehouseName())
                        ) {
                            APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                    i.intValue(),
                                    FieldConstant.WAREHOUSE_CODE,
                                    MessageCode.DATA_NOT_CHANGE,
                                    messageSource.getMessage(
                                            MessageCode.DATA_NOT_CHANGE, new Object[]{w.getWarehouseCd()}, locale
                                    )
                            );
                            APIErrorDetail apiErrorDetail2 = new APIErrorDetail(
                                    i.intValue(),
                                    FieldConstant.WAREHOUSE_NAME,
                                    MessageCode.DATA_NOT_CHANGE,
                                    messageSource.getMessage(
                                            MessageCode.DATA_NOT_CHANGE, new Object[]{w.getWarehouseCd()}, locale
                                    )
                            );
                            errorDetails.add(apiErrorDetail);
                            errorDetails.add(apiErrorDetail2);
                        }
                    }
                });

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.BAD_REQUEST)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                List<Warehouse> list = new ArrayList<>();
                for (Warehouse w: warehouseList) {
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

        }catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR ,
                    e.getMessage() ,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return createdWarehouse;
    }

    /**
     *
     * @return
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
     * @return
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
            result.setData(optionalWarehouse) ;
        }
        return result;
    }

    /**
     *
     * @param warehouseCd
     * @param warehouseName
     * @param page
     * @param limit
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getWarehousePage(String warehouseCd,String warehouseName, Integer page, Integer limit , String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        Pageable pageable = PageRequest.of(page, limit);
        Page<Warehouse> warehousePage = warehouseRepository.findByWarehouseCdContainingAndWarehouseNameContaining(warehouseCd , warehouseName ,pageable);
        if (warehousePage.getTotalElements() == 0) {
            response.setMessage(
                    messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    )
            );
        } else {
            response.setMessage(null);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(warehousePage);
        return  response;
    }

}
