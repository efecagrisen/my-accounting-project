package com.ecs.controller;

import com.ecs.dto.InvoiceDto;
import com.ecs.dto.InvoiceProductDto;
import com.ecs.entity.Invoice;
import com.ecs.enums.ClientVendorType;
import com.ecs.enums.InvoiceType;
import com.ecs.mapper.MapperUtil;
import com.ecs.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final SecurityService securityService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;

    public SalesInvoiceController(MapperUtil mapperUtil, InvoiceService invoiceService, InvoiceProductService invoiceProductService, SecurityService securityService, ClientVendorService clientVendorService, ProductService productService) {
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.securityService = securityService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
    }
        @GetMapping("/list")
        public String listAllSalesInvoices(Model model){

            List<InvoiceDto> salesInvoiceList = invoiceService.listAllByCompanyIdAndInvoiceType(securityService.getLoggedInUserCompanyId(), InvoiceType.SALES);

            model.addAttribute("invoices", salesInvoiceList);

            return "/invoice/sales-invoice-list";
        }

        @GetMapping("/create")
        public String createSalesInvoice(Model model){

            model.addAttribute("newSalesInvoice",invoiceService.generateNewInvoice(InvoiceType.SALES));
            model.addAttribute("clients", clientVendorService.listCompanyClientVendorsByType(ClientVendorType.CLIENT));

            return "/invoice/sales-invoice-create";
        }

        @PostMapping("/create")
        public String insertSalesInvoice(@ModelAttribute("newsalesInvoice") InvoiceDto invoiceDto, Model model){
            InvoiceDto invoiceToBeInserted = invoiceService.create(invoiceDto,InvoiceType.SALES);
            Invoice invoice = invoiceService.getTheLatestInvoiceByType(InvoiceType.SALES);

//        model.addAttribute("product",Arrays.asList(productService.listAllProducts()));
//        model.addAttribute("clients",clientVendorService.listCompanyClientVendorsByType(ClientVendorType.CLIENT));
            return "redirect:/salesInvoices/update/"+invoice.getId();
//        return "redirect:/salesInvoices/list";
        }

        @GetMapping("/update/{id}")
        public String editSalesInvoice(Model model, @PathVariable("id") Long invoiceId){

            InvoiceDto foundInvoice = invoiceService.findById(invoiceId);

            model.addAttribute("invoice",foundInvoice);
            model.addAttribute("newInvoiceProduct",new InvoiceProductDto());
            model.addAttribute("products",productService.listAllCompanyProducts());
            model.addAttribute("invoiceProducts",invoiceProductService.findByInvoiceId(invoiceId));
            model.addAttribute("clients",clientVendorService.listCompanyClientVendorsByType(ClientVendorType.CLIENT));


            return "/invoice/sales-invoice-update";
        }

        @PostMapping("/update/{id}")
        public String updateSalesInvoice(@PathVariable ("id") Long id, @ModelAttribute ("invoice") InvoiceDto invoiceWithNewFeatures){

            InvoiceDto foundInvoiceToBeUpdated = invoiceService.findById(id);

            invoiceService.update(foundInvoiceToBeUpdated,invoiceWithNewFeatures);

            return "redirect:/salesInvoices/list";
        }

        @PostMapping("/addInvoiceProduct/{id}")
        public String addInvoiceProduct(@PathVariable ("id") Long id,@ModelAttribute InvoiceProductDto invoiceProductDto,Model model){

            invoiceProductService.create(invoiceProductDto,id);

            return "redirect:/salesInvoices/update/"+id;
        }

        @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
        public String removeInvoiceProduct(@PathVariable ("invoiceId") Long invoiceId,@PathVariable ("invoiceProductId") Long invoiceProductId,@ModelAttribute InvoiceProductDto invoiceProductDto,Model model){

            invoiceProductService.removeInvoiceProductFromInvoice(invoiceId, invoiceProductId);

            return "redirect:/salesInvoices/update/"+invoiceId;
        }

        @GetMapping("/approve/{id}")
        public String approveSalesInvoice(@PathVariable ("id") Long id){

            invoiceService.approveInvoice(id);

            return "redirect:/salesInvoices/list";

        }

        @GetMapping("/print/{id}")
        public String printInvoice(Model model, @PathVariable ("id") Long id){

            model.addAttribute("company", securityService.getLoggedInUser().getCompany());
            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceId(id));

            return "invoice/invoice_print";
        }

        @GetMapping ("/delete/{id}")
        public String deleteInvoice(Model model, @PathVariable ("id") Long id){

            invoiceService.deleteById(id);

            return "redirect:/salesInvoices/list";
        }

    }






