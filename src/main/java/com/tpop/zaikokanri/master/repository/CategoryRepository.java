package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.constants.CategoryQueryConstant;
import com.tpop.zaikokanri.master.dto.CategoryDto;
import com.tpop.zaikokanri.master.dto.ICategoryDto;
import com.tpop.zaikokanri.master.entities.Category;
import com.tpop.zaikokanri.master.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {

    @Query(nativeQuery = true , value = CategoryQueryConstant.SEARCH_CATEGORY)
    Page<ICategoryDto> getCategory(@Param("categoryDto") CategoryDto categoryDto , Pageable pageable);

    Optional<Category> findCategoryByCategoryCd(String categoryCd);

}
