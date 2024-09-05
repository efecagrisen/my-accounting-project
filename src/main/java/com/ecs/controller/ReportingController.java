package com.ecs.controller;

import com.ecs.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/stockData")
    public String showStockReportList(Model model){

        model.addAttribute("invoiceProducts",reportingService.listAllApprovedCompanyInvoiceProducts());

        return "report/stock-report";
    }

    @GetMapping("/profitLossData")
    public String showProfitLossList(Model model){

        model.addAttribute("monthlyProfitLossDataMap",reportingService.getMonthlyProfitLossListAsMapEntries());

        return "report/profit-loss-report";
    }



}
