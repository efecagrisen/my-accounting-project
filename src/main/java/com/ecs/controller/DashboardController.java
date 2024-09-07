package com.ecs.controller;

import com.ecs.enums.ExchangeRatesApiPlaceHolderTemp;
import com.ecs.enums.InvoiceType;
import com.ecs.service.DashboardService;
import com.ecs.service.InvoiceService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final InvoiceService invoiceService;
    private final SecurityService securityService;
    private final DashboardService dashboardService;

    public DashboardController(InvoiceService invoiceService, SecurityService securityService, DashboardService dashboardService) {
        this.invoiceService = invoiceService;
        this.securityService = securityService;
        this.dashboardService = dashboardService;
    }


    @GetMapping
    public String dashboard(Model model){

        model.addAttribute("summaryNumbers", dashboardService.summaryNumbersCalculation());
        model.addAttribute("invoices", invoiceService.listCompaniesLastThreeApprovedInvoices());
        model.addAttribute("exchangeRates", ExchangeRatesApiPlaceHolderTemp.values().length);
        //todo api will be added for exchange rates

        return "/dashboard";
    }




}
