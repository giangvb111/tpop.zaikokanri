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
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<Category>> createCategory(@RequestBody List<Category> categoryList , Locale locale) throws CommonException {
        return ResponseEntity.ok(categoryService.save(categoryList , locale));
    }

    @GetMapping(value = "/get-category-by-id")
    public ResponseEntity<ApiResponse<Object>> getCategoryById(@RequestParam(name = "id") Integer categoryId , Locale locale) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId , locale));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getCategory(@ModelAttribute (name = "params") CategoryDto categoryDto ,
                                                           @RequestParam (value = "page", defaultValue = "0") Integer page,
                                                           @RequestParam (value = "limit", defaultValue = "100")  Integer limit) {
        return ResponseEntity.ok(categoryService.getCategory( categoryDto ,page ,limit));
    }

}
