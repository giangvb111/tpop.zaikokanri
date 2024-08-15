package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.entities.Category;
import com.tpop.zaikokanri.master.entities.Customer;
import com.tpop.zaikokanri.master.repository.CustomerRepository;
import com.tpop.zaikokanri.master.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements GenericService<Customer> {

    private final CustomerRepository customerRepository;

    private final MessageSource messageSource;

    @Override
    public Page<Customer> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    @Override
    public List<Customer> save(List<Customer> customerList, Locale locale) throws CommonException {

        List<Customer> createdCustomer  = new ArrayList<>();
        try {
            LocalDateTime current  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(customerList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                customerList.forEach(c ->{
                    if (c.getCustomerCd().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                "" ,
                                MessageCode.CHECK_EXISTS ,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS , new Object[]{""}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                });

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.CHECK_EXISTS)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                List<Customer> list = new ArrayList<>();
                for (Customer c: customerList) {
                    Customer category = Customer.builder()
                            .id(c.getId())
                            .customerCd(c.getCustomerCd())
                            .customerName(c.getCustomerName())
                            .customerNameFormal(c.getCustomerNameFormal())
                            .supplierFlag(c.getSupplierFlag())
                            .deliveryFlag(c.getDeliveryFlag())
                            .requestFlag(c.getRequestFlag())
                            .companyFlag(c.getCompanyFlag())
                            .country(c.getCountry())
                            .postalCode(c.getPostalCode())
                            .prefectures(c.getPrefectures())
                            .municipalities(c.getMunicipalities())
                            .address(c.getAddress())
                            .building(c.getBuilding())
                            .tel(c.getTel())
                            .fax(c.getFax())
                            .departmentName(c.getDepartmentName())
                            .picName(c.getPicName())
                            .mailAddress(c.getMailAddress())
                            .homePage(c.getHomePage())
                            .deliveryId(c.getDeliveryId())
                            .createdAt(current)
                            .createdBy("user")
                            .updatedAt(current)
                            .updatedBy("user")
                            .build();
                    list.add(category);
                }
                createdCustomer = customerRepository.saveAllAndFlush(list);
            }

        }catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR ,
                    e.getMessage() ,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return createdCustomer;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) {
        // TODO document why this method is empty
    }
}
