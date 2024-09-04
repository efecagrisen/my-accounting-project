package com.ecs.service;

import com.ecs.dto.InvoiceProductDto;

import java.util.List;

public interface ReportingService {

List<InvoiceProductDto> listAllApprovedCompanyInvoiceProducts();

}
