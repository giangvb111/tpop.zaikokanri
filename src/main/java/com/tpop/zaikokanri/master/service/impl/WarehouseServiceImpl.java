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
                                        MessageCode.NOT_BLANK , new Object[]{FieldConstant.WAREHOUSE_CODE}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (w.getWarehouseName().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_NAME,
                                MessageCode.CHECK_EXISTS ,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK , new Object[]{FieldConstant.WAREHOUSE_NAME}, locale
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
     * @param locale
     * @return
     */
    @Override
    public ApiResponse<Object> getWarehouseById(Integer warehouseId, Locale locale) {
        ApiResponse<Object> result = new ApiResponse<>();
        if (!Objects.isNull(warehouseId)) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
            if (optionalWarehouse.isEmpty()) {
                result.setMessage(messageSource.getMessage(
                        MessageCode.CHECK_EXISTS, null, locale
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
    public ApiResponse<Object> getWarehousePage(String warehouseCd,String warehouseName, Integer page, Integer limit) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page, limit);
        Page<Warehouse> warehousePage = warehouseRepository.findByWarehouseCdContainingAndWarehouseNameContaining(warehouseCd , warehouseName ,pageable);
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setMessage(null);
        response.setData(warehousePage);
        return  response;
    }

}
