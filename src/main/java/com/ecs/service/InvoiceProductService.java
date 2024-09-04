package com.ecs.service;

import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.entity.Invoice;
import com.ecs.enums.InvoiceType;

import java.util.List;

public interface InvoiceProductService {

    InvoiceProductDto findById(Long id);
    List<InvoiceProductDto> findByInvoiceId(Long id);

    List<InvoiceProductDto> listAll();

    InvoiceProductDto create(InvoiceProductDto invoiceProductDto, Long invoiceId);

    void removeInvoiceProductFromInvoice(Long invoiceId, Long invoiceProductId);

    void deleteById(Long invoiceProductId);
    void deleteByInvoice(InvoiceDto invoiceDto);
    void save(InvoiceProductDto invoiceProductDto);

    List<InvoiceProductDto> listPurchaseInvoiceProductsQuantityNotZero(Long companyId,String productName, InvoiceType invoiceType, int quantity);






}
