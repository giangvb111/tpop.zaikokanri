package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.FieldConstant;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.DivisionDto;
import com.tpop.zaikokanri.master.dto.IDivisionDto;
import com.tpop.zaikokanri.master.entities.Division;
import com.tpop.zaikokanri.master.entities.Location;
import com.tpop.zaikokanri.master.entities.Warehouse;
import com.tpop.zaikokanri.master.entities.WarehouseDivision;
import com.tpop.zaikokanri.master.repository.DivisionRepository;
import com.tpop.zaikokanri.master.repository.WarehouseDivisionRepository;
import com.tpop.zaikokanri.master.service.DivisionService;
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
public class DivisionServiceImpl implements DivisionService {

    private final DivisionRepository divisionRepository;

    private final WarehouseDivisionRepository warehouseDivisionRepository;

    private final MessageSource messageSource;

    /**
     * @param divisionCd
     * @param divisionName
     * @param page
     * @param limit
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getDivisionPage(String divisionCd, String divisionName, String warehouseName, Integer page, Integer limit, String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page, limit);
        Locale locale = Locale.forLanguageTag(lang);
        Page<IDivisionDto> divisionPage = divisionRepository.getDivisionPage(divisionCd, divisionName, warehouseName, pageable);
        if (divisionPage.getTotalElements() == 0) {
            response.setMessage(
                    messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    )
            );
        } else {
            response.setMessage(null);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(divisionPage);
        return response;
    }

    /**
     * @param divisionId
     * @param lang
     * @return
     */
    @Override
    public ApiResponse<Object> getDivisionById(Integer divisionId, String lang) throws CommonException {
        ApiResponse<Object> result = new ApiResponse<>();
        try {
            if (!Objects.isNull(divisionId)) {
                Locale locale = Locale.forLanguageTag(lang);
                List<IDivisionDto> division = divisionRepository.getDivisionByDivisionId(divisionId);
                if (CollectionUtils.isEmpty(division)) {
                    result.setMessage(messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    ));
                } else {
                    result.setMessage(null);
                }
                result.setStatus(ResponseStatusConst.SUCCESS);
                result.setData(division);
            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    @Override
    public Boolean getDivisionByDivisionCode(String divisionCode) {
        Optional<Division> division = divisionRepository.findDivisionByDivisionCd(divisionCode);
        return division.isPresent();
    }

    /**
     * @param divisionDtoList
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class, Exception.class})
    public List<IDivisionDto> createDivision(List<DivisionDto> divisionDtoList, String lang) throws CommonException {
        List<IDivisionDto> createdDivision;
        List<Division> createdDiv = new ArrayList<>();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            Locale locale = Locale.forLanguageTag(lang);
            if (!CollectionUtils.isEmpty(divisionDtoList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                divisionDtoList.forEach(d -> {

                    if (d.getDivisionCd().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.DIVISION_CD,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.DIVISION_CD, null, locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (d.getDivisionCd().length() > 10) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.DIVISION_CD,
                                MessageCode.LENGTH_EXCEEDED,
                                messageSource.getMessage(
                                        MessageCode.LENGTH_EXCEEDED, new Object[]{d.getDivisionCd()}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    if (Objects.isNull(d.getId()) && Boolean.TRUE.equals(getDivisionByDivisionCode(d.getDivisionCd()))) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.DIVISION_CD,
                                MessageCode.DATA_ALREADY_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.DATA_ALREADY_EXISTS, new Object[]{d.getDivisionCd()}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (d.getDivisionName().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.DIVISION_NAME,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.DIVISION_NAME, null, locale)
                                        }, locale
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

                List<Division> list = new ArrayList<>();
                for (DivisionDto d : divisionDtoList) {
                    Division division = Division.builder()
                            .id(d.getId())
                            .divisionCd(d.getDivisionCd())
                            .divisionName(d.getDivisionName())
                            .createdAt(currentTime)
                            .createdBy("user")
                            .updatedAt(currentTime)
                            .updatedBy("user")
                            .build();
                    list.add(division);
                }
                createdDiv = divisionRepository.saveAllAndFlush(list);

                List<WarehouseDivision> warehouseDivisionList = new ArrayList<>();
                for (int j = 0; j < divisionDtoList.size(); j++) {
                    DivisionDto divisionDto = divisionDtoList.get(j);
                    Integer divisionId = createdDiv.get(j).getId();
                    if (!CollectionUtils.isEmpty(divisionDto.getDivisionWarehouseList())) {
                        List<Integer> warehouseDivisionIdList = warehouseDivisionRepository.getWarehouseDivisionIdListByDivisionId(divisionId);
                        for (WarehouseDivision wd : divisionDto.getDivisionWarehouseList()) {
                            if (!Objects.isNull(wd.getDiviWarehouseId())) {
                                warehouseDivisionIdList.remove(wd.getDiviWarehouseId());
                            }
                            WarehouseDivision warehouseDivision = WarehouseDivision.builder()
                                    .diviWarehouseId(wd.getDiviWarehouseId())
                                    .divisionId(divisionId)
                                    .warehouseId(wd.getWarehouseId())
                                    .createdAt(currentTime)
                                    .createdBy("user")
                                    .updatedAt(currentTime)
                                    .updatedBy("user")
                                    .build();
                            warehouseDivisionList.add(warehouseDivision);
                        }
                        warehouseDivisionRepository.deleteAllByIdInBatch(warehouseDivisionIdList);
                    }
                }
                warehouseDivisionRepository.saveAllAndFlush(warehouseDivisionList);
            }

            createdDivision = divisionRepository.getDivisionByDivisionIdList(
                    createdDiv.stream().map(Division::getId).toList()
            );
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return createdDivision;
    }
}
