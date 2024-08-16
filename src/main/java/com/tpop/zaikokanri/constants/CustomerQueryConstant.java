package com.tpop.zaikokanri.constants;

public class CustomerQueryConstant {

    public static final String SEARCH_CUSTOMER =
            "SELECT * \n" +
                    "FROM M_取引先 as c\n" +
                    "WHERE (:#{#customerDto.customerCd} IS NULL OR c.CUSTOMER_CD LIKE CONCAT('%', :#{#customerDto.customerCd}, '%'))\n" +
                    "AND (:#{#customerDto.customerName} IS NULL OR c.CUSTOMER_NAME LIKE CONCAT('%', :#{#customerDto.customerName}, '%'))\n" +
                    "AND (:#{#customerDto.customerNameFormal} IS NULL OR c.[CUSTOMER_NAME(FORMAL)] LIKE CONCAT('%', :#{#customerDto.customerNameFormal}, '%'))\n" +
                    "AND (:#{#customerDto.supplierFlag} IS NULL OR c.SUPPLIER_FLAG LIKE CONCAT('%', :#{#customerDto.supplierFlag}, '%'))\n" +
                    "AND (:#{#customerDto.deliveryFlag} IS NULL OR c.DELIVERY_FLAG LIKE CONCAT('%', :#{#customerDto.deliveryFlag}, '%'))\n" +
                    "AND (:#{#customerDto.requestFlag} IS NULL OR c.REQUEST_FLAG LIKE CONCAT('%', :#{#customerDto.requestFlag}, '%'))\n" +
                    "AND (:#{#customerDto.companyFlag} IS NULL OR c.COMPANY_FLAG LIKE CONCAT('%', :#{#customerDto.companyFlag}, '%'))\n" +
                    "AND (:#{#customerDto.country} IS NULL OR c.COUNTRY LIKE CONCAT('%', :#{#customerDto.country}, '%'))\n" +
                    "AND (:#{#customerDto.postalCode} IS NULL OR c.POSTAL_CODE LIKE CONCAT('%', :#{#customerDto.postalCode}, '%'))\n" +
                    "AND (:#{#customerDto.prefectures} IS NULL OR c.PREFECTURES LIKE CONCAT('%', :#{#customerDto.prefectures}, '%'))\n" +
                    "AND (:#{#customerDto.municipalities} IS NULL OR c.MUNICIPALITIES LIKE CONCAT('%', :#{#customerDto.municipalities}, '%'))\n" +
                    "AND (:#{#customerDto.address} IS NULL OR c.ADDRESS LIKE CONCAT('%', :#{#customerDto.address}, '%'))\n" +
                    "AND (:#{#customerDto.building} IS NULL OR c.BUILDING LIKE CONCAT('%', :#{#customerDto.building}, '%'))\n" +
                    "AND (:#{#customerDto.tel} IS NULL OR c.TEL LIKE CONCAT('%', :#{#customerDto.tel}, '%'))\n" +
                    "AND (:#{#customerDto.fax} IS NULL OR c.FAX LIKE CONCAT('%', :#{#customerDto.fax}, '%'))\n" +
                    "AND (:#{#customerDto.departmentName} IS NULL OR c.DEPARTMENT_NAME LIKE CONCAT('%', :#{#customerDto.departmentName}, '%'))\n" +
                    "AND (:#{#customerDto.picName} IS NULL OR c.PIC_NAME LIKE CONCAT('%', :#{#customerDto.picName}, '%'))\n" +
                    "AND (:#{#customerDto.mailAddress} IS NULL OR c.MAIL_ADDRESS LIKE CONCAT('%', :#{#customerDto.mailAddress}, '%'))\n" +
                    "AND (:#{#customerDto.homePage} IS NULL OR c.HOME_PAGE LIKE CONCAT('%', :#{#customerDto.homePage}, '%'))";
}
