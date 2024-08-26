package com.tpop.zaikokanri.master.service;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.CustomerDto;
import com.tpop.zaikokanri.master.entities.Customer;

import java.util.List;

public interface CustomerService {

    ApiResponse<Object> getCustomerPage(CustomerDto customerDto , Integer page , Integer limit , String lang) throws CommonException;

    ApiResponse<Object> getCustomerById(Integer customerId, String lang);

    List<Customer> createdCustomer(List<Customer> customerList, String lang) throws CommonException;

    Boolean getCustomerByCustomerCode(String customerCode);

    ApiResponse<Object> deleteCustomerByIdList(List<Integer> customerIdList , String lang) throws CommonException;
}
