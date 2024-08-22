package com.ecs.controller;

import com.ecs.dto.ClientVendorDto;
import com.ecs.enums.ClientVendorType;
import com.ecs.enums.CountriesApiPlaceHolderTemp;
import com.ecs.mapper.MapperUtil;
import com.ecs.service.ClientVendorService;
import com.ecs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final UserService userService;
    private final MapperUtil mapperUtil;

    public ClientVendorController(ClientVendorService clientVendorService, UserService userService, MapperUtil mapperUtil) {
        this.clientVendorService = clientVendorService;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }


    @GetMapping("/list")
    public String listCompanyClientVendors(Model model){

        model.addAttribute("clientVendors",clientVendorService.listCompanyClientVendors());

        return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String createClientVendor(Model model){

        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("countries", CountriesApiPlaceHolderTemp.values());
//        model.addAttribute("clientVendorTypes", ClientVendorType.values());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String insertClientVendor(@ModelAttribute ("clientVendor") ClientVendorDto clientVendorDto){

        clientVendorService.save(clientVendorDto);

        return "redirect:/clientVendors/list";
    }


    @GetMapping("/update/{id}")
    public String editClientVendor(@PathVariable("id") Long id, Model model){

        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("countries", CountriesApiPlaceHolderTemp.values());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String updateClientVendor(@ModelAttribute("clientVendor") ClientVendorDto clientVendorDto){

        clientVendorService.save(clientVendorDto);

        return "redirect:/clientVendors/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id){

        clientVendorService.deleteById(id);

        return "redirect:/clientVendors/list";

    }



}
