package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.CategoryDto;
import com.tpop.zaikokanri.master.entities.Category;
import com.tpop.zaikokanri.master.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<Category>> createCategory(@RequestBody List<Category> categoryList ,  @RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(categoryService.createCategory(categoryList , lang));
    }

    @GetMapping(value = "/get-category-by-id")
    public ResponseEntity<ApiResponse<Object>> getCategoryById(@RequestParam(name = "id") Integer categoryId , @RequestParam(value = "lang") String lang) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId , lang));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getCategory(@ModelAttribute (name = "params") CategoryDto categoryDto ,
                                                           @RequestParam(value = "lang") String lang,
                                                           @RequestParam (value = "page", defaultValue = "0") Integer page,
                                                           @RequestParam (value = "limit", defaultValue = "100")  Integer limit) throws CommonException {
        return ResponseEntity.ok(categoryService.getCategoryPage( categoryDto ,page ,limit ,lang));
    }

    @DeleteMapping(value = "/delete-by-id-list")
    public ResponseEntity<ApiResponse<Object>> deleteByIdList(@RequestBody List<Integer> categoryIdList ,  @RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(categoryService.deleteCategoryByIdList(categoryIdList , lang));
    }

}
