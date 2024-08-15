package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.DivisionDto;
import com.tpop.zaikokanri.master.dto.IDivisionDto;

import java.util.List;
import java.util.Locale;

public interface DivisionService {

    ApiResponse<Object> getDivisionPage(String divisionCd , String divisionName , Integer page , Integer limit) throws CommonException;

    ApiResponse<Object> getDivisionById(Integer divisionId, Locale locale) throws CommonException;

    List<IDivisionDto> createDivision(List<DivisionDto> divisionList, Locale locale) throws CommonException;
}
