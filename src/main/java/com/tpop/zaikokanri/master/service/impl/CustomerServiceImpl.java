package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.FieldConstant;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.CustomerDto;
import com.tpop.zaikokanri.master.dto.ICustomerDto;
import com.tpop.zaikokanri.master.entities.Customer;
import com.tpop.zaikokanri.master.repository.CustomerRepository;
import com.tpop.zaikokanri.master.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final MessageSource messageSource;

    /**
     *
     * @param customerDto
     * @param page
     * @param limit
     * @param lang
     * @return Customer Page
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getCustomerPage(CustomerDto customerDto, Integer page, Integer limit, String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ICustomerDto> customerPage = customerRepository.getCustomerPage(customerDto ,pageable);
        if (customerPage.getTotalElements() == 0) {
            /* case of no data */
            response.setMessage(
                    messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    )
            );
        } else {
            /* case of data */
            response.setMessage(null);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(customerPage);
        return  response;
    }

    /**
     *
     * @param customerId
     * @param lang
     * @return Customer
     */
    @Override
    public ApiResponse<Object> getCustomerById(Integer customerId, String lang) {
        ApiResponse<Object> result = new ApiResponse<>();
        if (!Objects.isNull(customerId)) {
            Locale locale = Locale.forLanguageTag(lang);
            Optional<Customer> optionalWarehouse = customerRepository.findById(customerId);
            if (optionalWarehouse.isEmpty()) {
                result.setMessage(messageSource.getMessage(
                        MessageCode.DATA_NOT_FOUND, null, locale
                ));
            } else {
                result.setMessage(null);
            }
            result.setStatus(ResponseStatusConst.SUCCESS);
            result.setData(optionalWarehouse);
        }
        return result;
    }

    /**
     * Create Customer
     * @param customerList
     * @param lang
     * @return Created Customer List
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class, Exception.class})
    public List<Customer> createdCustomer(List<Customer> customerList, String lang) throws CommonException {
        List<Customer> createdCustomer = new ArrayList<>();
        try {
            LocalDateTime current = LocalDateTime.now();
            Locale locale = Locale.forLanguageTag(lang);
            if (!CollectionUtils.isEmpty(customerList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                /* check Customer List */
                customerList.forEach(c -> {
                    if (c.getCustomerCd().isBlank()) {
                        /* Case Customer Code Blank  */
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.CUSTOMER_CD,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.CUSTOMER_CD, null, locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    /* Case Customer Code Already Exists In Database  */
                    if (Objects.isNull(c.getId()) && Boolean.TRUE.equals(getCustomerByCustomerCode(c.getCustomerCd()))) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.WAREHOUSE_CODE,
                                MessageCode.DATA_ALREADY_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.DATA_ALREADY_EXISTS, new Object[]{c.getCustomerCd()}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                });

                /* If errorDetails is not empty, an exception will be thrown.  */
                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.BAD_REQUEST)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                /* add each element to the list and then execute save all  */
                List<Customer> list = new ArrayList<>();
                for (Customer c : customerList) {
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

        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return createdCustomer;
    }

    /**
     * Get Customer by Customer Code
     * @param customerCode
     * @return If Customer exists return True
     */
    @Override
    public Boolean getCustomerByCustomerCode(String customerCode) {
        Optional<Customer> customer = customerRepository.findCustomerByCustomerCd(customerCode);
        return customer.isPresent();
    }

    /**
     * Delete Customer By Customer Id List
     * @param customerIdList
     * @param lang
     * @return
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class, Exception.class})
    public ApiResponse<Object> deleteCustomerByIdList(List<Integer> customerIdList, String lang) {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        if (!CollectionUtils.isEmpty(customerIdList)) {
            /* If customerIdList not Empty => Delete all  */
            customerRepository.deleteAllById(customerIdList);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setMessage(
                messageSource.getMessage(MessageCode.DELETE_SUCCESS , null, locale)
        );
        response.setData(null);
        return response;
    }
}
