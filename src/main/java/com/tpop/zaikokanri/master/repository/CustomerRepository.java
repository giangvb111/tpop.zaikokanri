package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.constants.CustomerQueryConstant;
import com.tpop.zaikokanri.master.dto.CustomerDto;
import com.tpop.zaikokanri.master.dto.ICustomerDto;
import com.tpop.zaikokanri.master.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByCustomerCd(String customerCode);

    @Query(nativeQuery = true , value = CustomerQueryConstant.SEARCH_CUSTOMER)
    Page<ICustomerDto> getCustomerPage(CustomerDto customerDto , Pageable pageable);
}
