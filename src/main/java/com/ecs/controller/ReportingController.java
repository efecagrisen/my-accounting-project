package com.ecs.controller;

import com.ecs.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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



}
