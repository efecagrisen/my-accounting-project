package com.ecs.service;

import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.entity.Invoice;
import com.ecs.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    List<InvoiceDto> listAllInvoices();


    List<InvoiceDto> listAllByCompanyIdAndInvoiceType(Long companyId,InvoiceType invoiceType);

    InvoiceDto findById(Long id);

    void save(InvoiceDto invoiceDto);

    void update(InvoiceDto foundInvoiceDtoToBeUpdated, InvoiceDto invoiceWithNewFeatures);

    void deleteById(Long id);

    BigDecimal calculateTax(List<InvoiceProductDto> invoiceProductDtoList);
    BigDecimal calculateTaxForProduct(InvoiceProductDto invoiceProductDto);

    BigDecimal calculateTotalWithoutTax(List<InvoiceProductDto> invoiceProductDtoList);

    BigDecimal calculateTotalWithTax(List<InvoiceProductDto> invoiceProductDtoList);

    String generateNextInvoiceNumber(Invoice lastInvoice, InvoiceType invoiceType);

    Invoice getTheLatestInvoiceByType(InvoiceType invoiceType);

    InvoiceDto generateNewInvoice(InvoiceType invoiceType);
    InvoiceDto create(InvoiceDto invoiceDto, InvoiceType invoiceType);

}
