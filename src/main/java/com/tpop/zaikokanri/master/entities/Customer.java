package com.tpop.zaikokanri.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "M_取引先")
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Integer id;

    @Column(name = "CUSTOMER_CD", nullable = false)
    private String customerCd;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "\"CUSTOMER_NAME(FORMAL)\"")
    private String customerNameFormal;

    @Column(name = "SUPPLIER_FLAG")
    private Integer supplierFlag;

    @Column(name = "DELIVERY_FLAG")
    private Integer deliveryFlag;

    @Column(name = "REQUEST_FLAG")
    private Integer requestFlag;

    @Column(name = "COMPANY_FLAG")
    private Integer companyFlag;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "PREFECTURES")
    private String prefectures;

    @Column(name = "MUNICIPALITIES")
    private String municipalities;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "BUILDING")
    private String building;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @Column(name = "PIC_NAME")
    private String picName;

    @Column(name = "MAIL_ADRESS")
    private String mailAddress;

    @Column(name = "HOME_PAGE")
    private String homePage;

    @Column(name = "DELIVERY_ID")
    private Integer deliveryId;

    public Customer() {
    }

    public Customer(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }

    @Builder
    public Customer(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy,
                    Integer id, String customerCd, String customerName, String customerNameFormal,
                    Integer supplierFlag, Integer deliveryFlag, Integer requestFlag, Integer companyFlag,
                    String country, String postalCode, String prefectures, String municipalities, String address,
                    String building, String tel, String fax, String departmentName, String picName, String mailAddress, String homePage, Integer deliveryId) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.id = id;
        this.customerCd = customerCd;
        this.customerName = customerName;
        this.customerNameFormal = customerNameFormal;
        this.supplierFlag = supplierFlag;
        this.deliveryFlag = deliveryFlag;
        this.requestFlag = requestFlag;
        this.companyFlag = companyFlag;
        this.country = country;
        this.postalCode = postalCode;
        this.prefectures = prefectures;
        this.municipalities = municipalities;
        this.address = address;
        this.building = building;
        this.tel = tel;
        this.fax = fax;
        this.departmentName = departmentName;
        this.picName = picName;
        this.mailAddress = mailAddress;
        this.homePage = homePage;
        this.deliveryId = deliveryId;
    }
}