package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Location;
import com.tpop.zaikokanri.master.entities.Warehouse;
import com.tpop.zaikokanri.master.service.impl.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/location")
public class LocationController {

    private final LocationServiceImpl locationService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<Location>> createLocation(@RequestBody List<Location> locationList, Locale locale) throws CommonException {
        return ResponseEntity.ok(locationService.createLocation(locationList, locale));
    }

    @GetMapping(value = "/get-location-by-id")
    public ResponseEntity<ApiResponse<Object>> getLocationById(@RequestParam(name = "id") Integer locationId, Locale locale) {
        return ResponseEntity.ok(locationService.getLocationById(locationId, locale));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getLocationPage(@RequestParam(value = "locationCd") String locationCd,
                                                            @RequestParam(value = "locationName") String locationName,
                                                            @RequestParam(value = "warehouseName") String warehouseName,
                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "limit", defaultValue = "100") Integer limit) throws CommonException {
        return ResponseEntity.ok(locationService.getLocationPage(locationCd, locationName,warehouseName, page, limit));
    }
}
