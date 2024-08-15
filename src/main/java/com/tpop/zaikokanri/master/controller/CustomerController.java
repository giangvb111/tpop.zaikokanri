package com.tpop.zaikokanri.master.controller;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.CustomerDto;
import com.tpop.zaikokanri.master.entities.Customer;
import com.tpop.zaikokanri.master.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping(value = "/create")
    public ResponseEntity<List<Customer>> createdCustomer(@RequestBody List<Customer> customerList, @RequestParam(value = "lang") String lang) throws CommonException {
        return ResponseEntity.ok(customerService.createdCustomer(customerList, lang));
    }

    @GetMapping(value = "/get-customer-by-id")
    public ResponseEntity<ApiResponse<Object>> getCustomerById(@RequestParam(name = "id") Integer customerId, @RequestParam(value = "lang") String lang) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId, lang));
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getCustomerPage(
                                                            @ModelAttribute("customerDto") CustomerDto customerDto,
                                                            @RequestParam(value = "lang") String lang,
                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "limit", defaultValue = "100") Integer limit) throws CommonException {
        return ResponseEntity.ok(customerService.getCustomerPage(customerDto, page, limit ,lang));
    }

}
