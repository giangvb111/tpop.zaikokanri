package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Location;
import com.tpop.zaikokanri.master.service.impl.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/location")
public class LocationController {

    private final LocationServiceImpl locationService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<Location>> createLocation(@RequestBody List<Location> locationList, @RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(locationService.createLocation(locationList, lang));
    }

    @GetMapping(value = "/get-location-by-id")
    public ResponseEntity<ApiResponse<Object>> getLocationById(@RequestParam(name = "id") Integer locationId, @RequestParam(value = "lang") String lang) {
        return ResponseEntity.ok(locationService.getLocationById(locationId, lang));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getLocationPage(@RequestParam(value = "locationCd") String locationCd,
                                                            @RequestParam(value = "locationName") String locationName,
                                                            @RequestParam(value = "warehouseName") String warehouseName,
                                                            @RequestParam(value = "lang") String lang,
                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "limit", defaultValue = "100") Integer limit) throws CommonException {
        return ResponseEntity.ok(locationService.getLocationPage(locationCd, locationName,warehouseName, page, limit ,lang));
    }

    @DeleteMapping(value = "/delete-by-id-list")
    public ResponseEntity<ApiResponse<Object>> deleteByIdList(@RequestBody List<Integer> locationIdList ,  @RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(locationService.deleteLocationByIdList(locationIdList , lang));
    }
}
