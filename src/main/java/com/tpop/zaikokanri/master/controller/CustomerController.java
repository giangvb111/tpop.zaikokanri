package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Customer;
import com.tpop.zaikokanri.master.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<Customer>> createCategory(@RequestBody List<Customer> customerList , Locale locale) throws CommonException {
        return ResponseEntity.ok(customerService.save(customerList , locale));
    }

//    @GetMapping(value = "/get-category-by-id")
//    public ResponseEntity<ApiResponse<Object>> getCategoryById(@RequestParam(name = "id") Integer categoryId , Locale locale) {
//        return ResponseEntity.ok(customerService.getCategoryById(categoryId , locale));
//    }
//
//    @GetMapping(value = "/get-list")
//    public ResponseEntity<ApiResponse<Object>> getCategory(@ModelAttribute (name = "params") CategoryDto categoryDto ,
//                                                           @RequestParam (value = "page", defaultValue = "0") Integer page,
//                                                           @RequestParam (value = "limit", defaultValue = "100")  Integer limit) {
//        return ResponseEntity.ok(customerService.getCategory( categoryDto ,page ,limit));
//    }

}
