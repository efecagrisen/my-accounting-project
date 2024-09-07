package com.ecs.service.impl;

import com.ecs.dto.InvoiceDto;
import com.ecs.enums.InvoiceStatus;
import com.ecs.enums.InvoiceType;
import com.ecs.service.DashboardService;
import com.ecs.service.InvoiceService;
import com.ecs.service.ReportingService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceService invoiceService;
    private final ReportingService reportingService;
    private final SecurityService securityService;

    public DashboardServiceImpl(InvoiceService invoiceService, ReportingService reportingService, SecurityService securityService) {
        this.invoiceService = invoiceService;
        this.reportingService = reportingService;
        this.securityService = securityService;
    }


    @Override
    public Map<String, BigDecimal> summaryNumbersCalculation() {

        Map<String,BigDecimal> summaryNumbers = new HashMap<>();

        BigDecimal totalPurchaseCost = invoiceService.listAllByCompanyIdAndInvoiceType(securityService.getLoggedInUserCompanyId(), InvoiceType.PURCHASE)
                                        .stream()
                                        .filter(invoiceDto -> invoiceDto.getInvoiceStatus()==InvoiceStatus.APPROVED)
                                        .map(InvoiceDto::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);


        BigDecimal totalSalesAmount = invoiceService.listAllByCompanyIdAndInvoiceType(securityService.getLoggedInUserCompanyId(), InvoiceType.SALES)
                                        .stream()
                                        .filter(invoiceDto -> invoiceDto.getInvoiceStatus()==InvoiceStatus.APPROVED)
                                        .map(InvoiceDto::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);


        List<Map.Entry<String,BigDecimal>> monthlyProfitLosslist = reportingService.getMonthlyProfitLossListAsMapEntries();

        BigDecimal totalProfitLoss = BigDecimal.ZERO;

        for (Map.Entry<String,BigDecimal> entry : monthlyProfitLosslist){
            BigDecimal profitLoss = entry.getValue();
            totalProfitLoss = totalProfitLoss.add(profitLoss);
        }

        summaryNumbers.put("totalCost", totalPurchaseCost);
        summaryNumbers.put("totalSales", totalSalesAmount);
        summaryNumbers.put("profitLoss", totalProfitLoss);

        return summaryNumbers;
    }
}
