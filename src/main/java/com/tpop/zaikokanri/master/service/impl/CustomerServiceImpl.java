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
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getCustomerPage(CustomerDto customerDto, Integer page, Integer limit, String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ICustomerDto> customerPage = customerRepository.getCustomerPage(customerDto ,pageable);
        if (customerPage.getTotalElements() == 0) {
            response.setMessage(
                    messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    )
            );
        } else {
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
     * @return
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
     * @param customerList
     * @param lang
     * @return
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
                customerList.forEach(c -> {
                    if (c.getCustomerCd().isBlank()) {
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

                    if (Boolean.TRUE.equals(getCustomerByCustomerCode(c.getCustomerCd()))) {
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

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.BAD_REQUEST)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

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
     * @param customerCode
     * @return
     */
    @Override
    public Boolean getCustomerByCustomerCode(String customerCode) {
        Optional<Customer> warehouse = customerRepository.findCustomerByCustomerCd(customerCode);
        return warehouse.isPresent();
    }
}
