package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.constants.DivisionQueryConstant;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.IDivisionDto;
import com.tpop.zaikokanri.master.entities.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {

    @Query(nativeQuery = true , value = DivisionQueryConstant.SEARCH_DIVISION_BY_DIVISION_ID)
    List<IDivisionDto> getDivisionByDivisionId (Integer divisionId) throws CommonException;

    @Query(nativeQuery = true , value = DivisionQueryConstant.SEARCH_DIVISION)
    Page<IDivisionDto> getDivisionPage (String divisionCd, String divisionName , Pageable pageable) throws CommonException;

}
