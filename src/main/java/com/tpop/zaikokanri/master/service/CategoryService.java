package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.CategoryDto;
import com.tpop.zaikokanri.master.entities.Category;
import com.tpop.zaikokanri.master.entities.Warehouse;

import java.util.List;

public interface CategoryService {

    ApiResponse<Object> getCategoryPage(CategoryDto categoryDto , Integer page , Integer limit , String lang) throws CommonException;

    ApiResponse<Object> getCategoryById(Integer categoryId, String lang);

    List<Category> createCategory(List<Category> categoryList, String lang) throws CommonException;

    Boolean getCategoryByCategoryCode(String categoryCode);

    ApiResponse<Object> deleteCategoryByIdList(List<Integer> categoryIdList , String lang) throws CommonException;
}
