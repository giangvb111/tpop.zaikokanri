package com.tpop.zaikokanri.master.repository;


import com.tpop.zaikokanri.constants.LocationQueryConstant;
import com.tpop.zaikokanri.master.dto.ILocationDto;
import com.tpop.zaikokanri.master.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(nativeQuery = true , value = LocationQueryConstant.SEARCH_LOCATION_BY_LOCATION_ID)
    ILocationDto getLocationsByLocationId(Integer locationId);

    @Query(nativeQuery = true , value = LocationQueryConstant.SEARCH_LOCATION_PAGE)
    Page<ILocationDto> getLocationsPage(String locationCd, String locationName, String warehouseName,Pageable pageable);

    Optional<Location> findLocationByLocationCd(String locationCd);
}