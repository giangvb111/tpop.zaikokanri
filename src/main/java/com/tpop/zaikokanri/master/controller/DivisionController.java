package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.DivisionDto;
import com.tpop.zaikokanri.master.dto.IDivisionDto;
import com.tpop.zaikokanri.master.entities.Division;
import com.tpop.zaikokanri.master.service.impl.DivisionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/division")
public class DivisionController {

    private final DivisionServiceImpl divisionService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<IDivisionDto>> createDivision(@RequestBody List<DivisionDto> divisionDtoList, Locale locale) throws CommonException {
        return ResponseEntity.ok(divisionService.createDivision(divisionDtoList, locale));
    }

    @GetMapping(value = "/get-division-by-id")
    public ResponseEntity<ApiResponse<Object>> getDivisionById(@RequestParam(name = "id") Integer divisionId, Locale locale) throws CommonException {
        return ResponseEntity.ok(divisionService.getDivisionById(divisionId, locale));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getDivisionPage(@RequestParam(value = "divisionCd") String divisionCd,
                                                            @RequestParam(value = "divisionName") String divisionName,
                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "limit", defaultValue = "100") Integer limit) throws CommonException {
        return ResponseEntity.ok(divisionService.getDivisionPage(divisionCd,divisionName, page, limit));
    }
}
