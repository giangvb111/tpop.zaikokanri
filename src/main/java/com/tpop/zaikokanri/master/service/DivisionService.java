package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.DivisionDto;
import com.tpop.zaikokanri.master.dto.IDivisionDto;

import java.util.List;

public interface DivisionService {

    ApiResponse<Object> getDivisionPage(String divisionCd, String divisionName, String warehouseCd, Integer page, Integer limit, String lang) throws CommonException;

    ApiResponse<Object> getDivisionById(Integer divisionId, String lang) throws CommonException;

    List<IDivisionDto> createDivision(List<DivisionDto> divisionList, String lang) throws CommonException;

    Boolean getDivisionByDivisionCode(String divisionCode);
}
