package com.ecs.dto;

import com.ecs.enums.InvoiceStatus;
import com.ecs.enums.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDto {

    private Long id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate date;
    private CompanyDto companyDto;
    private ClientVendorDto clientVendorDto;
    private BigDecimal price;
    private BigDecimal tax;
    private BigDecimal total;

}
