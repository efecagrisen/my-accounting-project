package com.ecs.service;

import com.ecs.dto.InvoiceProductDto;
import com.ecs.enums.InvoiceType;

import java.util.List;

public interface InvoiceProductService {

    InvoiceProductDto findById(Long id);
    List<InvoiceProductDto> findByInvoiceId(Long id);

    List<InvoiceProductDto> listAll();

    InvoiceProductDto create(InvoiceProductDto invoiceProductDto, Long invoiceId);






}
