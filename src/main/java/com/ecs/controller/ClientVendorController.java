package com.ecs.controller;

import com.ecs.dto.ClientVendorDto;
import com.ecs.enums.ClientVendorType;
import com.ecs.enums.CountriesApiPlaceHolderTemp;
import com.ecs.mapper.MapperUtil;
import com.ecs.service.ClientVendorService;
import com.ecs.service.InvoiceService;
import com.ecs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final UserService userService;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;

    public ClientVendorController(ClientVendorService clientVendorService, UserService userService, MapperUtil mapperUtil, InvoiceService invoiceService) {
        this.clientVendorService = clientVendorService;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
    }


    @GetMapping("/list")
    public String listCompanyClientVendors(Model model){

        List<ClientVendorDto> clientVendorDtoList = clientVendorService.listCompanyClientVendors();

            clientVendorDtoList.forEach(clientVendorDto -> {
                    if (invoiceService.existsByClientVendorId(clientVendorDto.getId())) {
                        clientVendorDto.setHasInvoice(true);
                    }
            });

        model.addAttribute("clientVendors", clientVendorDtoList);

        return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String createClientVendor(Model model){

        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("countries", clientVendorService.getCountries());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String insertClientVendor(Model model,@Valid @ModelAttribute ("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult){

        bindingResult = clientVendorService.checkClientVendorNameExistsByType(clientVendorDto.getClientVendorName(),clientVendorDto.getClientVendorType(),bindingResult);

        if (bindingResult.hasFieldErrors()){
            model.addAttribute("countries", clientVendorService.getCountries());
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

            return "clientVendor/clientVendor-create";
        }

        clientVendorService.save(clientVendorDto);

        return "redirect:/clientVendors/list";
    }


    @GetMapping("/update/{id}")
    public String editClientVendor(@PathVariable("id") Long id, Model model){

        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("countries", clientVendorService.getCountries());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String updateClientVendor(@Valid @ModelAttribute("clientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model){

        if (bindingResult.hasFieldErrors()){
            model.addAttribute("countries", clientVendorService.getCountries());
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

            return "clientVendor/clientVendor-update";
        }

        clientVendorService.save(clientVendorDto);

        return "redirect:/clientVendors/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id){

        clientVendorService.deleteById(id);

        return "redirect:/clientVendors/list";

    }



}
