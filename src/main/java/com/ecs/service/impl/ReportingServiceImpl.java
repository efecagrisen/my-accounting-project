package com.ecs.service.impl;

import com.ecs.dto.InvoiceProductDto;
import com.ecs.entity.InvoiceProduct;
import com.ecs.enums.InvoiceStatus;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.InvoiceProductRepository;
import com.ecs.service.InvoiceProductService;
import com.ecs.service.InvoiceService;
import com.ecs.service.ReportingService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final InvoiceProductRepository invoiceProductRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    public ReportingServiceImpl(InvoiceService invoiceService, InvoiceProductService invoiceProductService, InvoiceProductRepository invoiceProductRepository, SecurityService securityService, MapperUtil mapperUtil) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<InvoiceProductDto> listAllApprovedCompanyInvoiceProducts() {

        return invoiceProductService.listAllCompanyInvoiceProducts();
    }
}
