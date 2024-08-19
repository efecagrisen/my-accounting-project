package com.ecs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {


    @GetMapping("/list")
    public String list(){


        return "/invoice/sales-invoice-list";
    }




}
