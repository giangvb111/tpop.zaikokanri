package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.DivisionDto;
import com.tpop.zaikokanri.master.dto.IDivisionDto;
import com.tpop.zaikokanri.master.entities.Division;
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
     *
     * @param divisionCd
     * @param divisionName
     * @param page
     * @param limit
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getDivisionPage(String divisionCd, String divisionName, Integer page, Integer limit) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page, limit);
        Page<IDivisionDto> divisionPage = divisionRepository.getDivisionPage(divisionCd, divisionName, pageable);
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setMessage(null);
        response.setData(divisionPage);
        return response;
    }

    /**
     * @param divisionId
     * @param locale
     * @return
     */
    @Override
    public ApiResponse<Object> getDivisionById(Integer divisionId, Locale locale) throws CommonException {
        ApiResponse<Object> result = new ApiResponse<>();
        try {
            if (!Objects.isNull(divisionId)) {
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

    /**
     * @param divisionDtoList
     * @param locale
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public List<IDivisionDto> createDivision(List<DivisionDto> divisionDtoList, Locale locale) throws CommonException {
        List<IDivisionDto> createdDivision = new ArrayList<>();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(divisionDtoList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                divisionDtoList.forEach(d -> {
                    if (d.getDivisionName().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                "divisionName",
                                MessageCode.CHECK_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS, new Object[]{"divisionName"}, locale
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
                List<Division> createdDiv = divisionRepository.saveAllAndFlush(list);

                List<WarehouseDivision> warehouseDivisionList = new ArrayList<>();
                for (int j = 0; j < divisionDtoList.size(); j++) {
                    DivisionDto divisionDto = divisionDtoList.get(j);
                    Integer divisionId = createdDiv.get(j).getId();
                    for (Integer warehouseId : divisionDto.getWarehouseIdList()) {
                        WarehouseDivision warehouseDivision = WarehouseDivision.builder()
//                                    .diviWarehouseId(wd.)
                                .divisionId(divisionId)
                                .warehouseId(warehouseId)
                                .createdAt(currentTime)
                                .createdBy("user")
                                .updatedAt(currentTime)
                                .updatedBy("user")
                                .build();
                        warehouseDivisionList.add(warehouseDivision);
                    }
                }
                warehouseDivisionRepository.saveAllAndFlush(warehouseDivisionList);
            }

//            createdDivision = divisionRepository.getDivisionByDivisionId(
//                    createdDivision.stream().map(Division::getId).toList()
//            );
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
