package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.constants.WarehouseDivisionQueryConstant;
import com.tpop.zaikokanri.master.entities.WarehouseDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseDivisionRepository extends JpaRepository<WarehouseDivision, Integer> {

    @Query(nativeQuery = true , value = WarehouseDivisionQueryConstant.SEARCH_WAREHOUSE_DIVISION_ID_BY_DIVISION_ID)
    List<Integer> getWarehouseDivisionIdListByDivisionId(Integer divisionId);

    @Modifying
    @Query(value = WarehouseDivisionQueryConstant.DELETE_BY_DIVISION_ID , nativeQuery=true)
    void deleteAllByDivisionIdList(List<Integer> divisionIdList);
}