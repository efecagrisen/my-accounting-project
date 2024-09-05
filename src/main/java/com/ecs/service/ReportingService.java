package com.ecs.service;

import com.ecs.dto.InvoiceProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReportingService {

List<InvoiceProductDto> listAllApprovedCompanyInvoiceProducts();

List<Map.Entry<String,BigDecimal>> getMonthlyProfitLossListAsMapEntries();
}
