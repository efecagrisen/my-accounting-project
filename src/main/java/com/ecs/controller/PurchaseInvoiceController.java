package com.ecs.controller;

import com.ecs.dto.CompanyDto;
import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.entity.Invoice;
import com.ecs.enums.ClientVendorType;
import com.ecs.enums.InvoiceType;
import com.ecs.mapper.MapperUtil;
import com.ecs.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final SecurityService securityService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;

    public PurchaseInvoiceController(MapperUtil mapperUtil, InvoiceService invoiceService, InvoiceProductService invoiceProductService, SecurityService securityService, ClientVendorService clientVendorService, ProductService productService) {
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.securityService = securityService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listAllPurchaseInvoices(Model model){

        List<InvoiceDto> purchaseInvoiceList = invoiceService.listAllByCompanyIdAndInvoiceType(securityService.getLoggedInUserCompanyId(), InvoiceType.PURCHASE);

        model.addAttribute("invoices", purchaseInvoiceList);

        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String createPurchaseInvoice(Model model){

        model.addAttribute("newPurchaseInvoice",invoiceService.generateNewInvoice(InvoiceType.PURCHASE));
        model.addAttribute("vendors", clientVendorService.listCompanyClientVendorsByType(ClientVendorType.VENDOR));

        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String insertPurchaseInvoice(@ModelAttribute ("newPurchaseInvoice") InvoiceDto invoiceDto,Model model){
        InvoiceDto invoiceToBeInserted = invoiceService.create(invoiceDto,InvoiceType.PURCHASE);
        Invoice invoice = invoiceService.getTheLatestInvoiceByType(InvoiceType.PURCHASE);

//        model.addAttribute("product",Arrays.asList(productService.listAllProducts()));
//        model.addAttribute("vendors",clientVendorService.listCompanyClientVendorsByType(ClientVendorType.VENDOR));
        return "redirect:/purchaseInvoices/update/"+invoice.getId();
//        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/update/{id}")
    public String editPurchaseInvoice(Model model, @PathVariable ("id") Long invoiceId){

        InvoiceDto foundInvoice = invoiceService.findById(invoiceId);

        model.addAttribute("invoice",foundInvoice);
        model.addAttribute("newInvoiceProduct",new InvoiceProductDto());
        model.addAttribute("products",productService.listAllProducts());
        model.addAttribute("invoiceProducts",invoiceProductService.findByInvoiceId(invoiceId));
        model.addAttribute("vendors",clientVendorService.listCompanyClientVendorsByType(ClientVendorType.VENDOR));


        return "/invoice/purchase-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updatePurchaseInvoice(@PathVariable ("id") Long id, @ModelAttribute ("invoice") InvoiceDto invoiceWithNewFeatures){

        InvoiceDto foundInvoiceToBeUpdated = invoiceService.findById(id);

        invoiceService.update(foundInvoiceToBeUpdated,invoiceWithNewFeatures);

        return "redirect:/purchaseInvoices/update/"+id;
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable ("id") Long id,@ModelAttribute InvoiceProductDto invoiceProductDto,Model model){

        invoiceProductService.create(invoiceProductDto,id);

        return "redirect:/purchaseInvoices/update/"+id;
    }


}
