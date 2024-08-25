package com.ecs.controller;

import com.ecs.dto.CompanyDto;
import com.ecs.enums.CountriesApiPlaceHolderTemp;
import com.ecs.mapper.MapperUtil;
import com.ecs.service.CompanyService;
import com.ecs.service.RoleService;
import com.ecs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final UserService userService;
    private final RoleService roleService;

    public CompanyController(MapperUtil mapperUtil, CompanyService companyService, UserService userService, RoleService roleService) {
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public String listAllCompanies(Model model){

        model.addAttribute("companies",companyService.getCompanyDtoByLoggedInUser());

        return "/company/company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model){

        model.addAttribute("newCompany",new CompanyDto());
        model.addAttribute("countries", CountriesApiPlaceHolderTemp.values());

        return "company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(Model model, @Valid @ModelAttribute ("newCompany") CompanyDto newCompany, BindingResult bindingResult){

        bindingResult = companyService.addTitleValidation(newCompany.getTitle(), bindingResult);

        if (bindingResult.hasFieldErrors()){
            model.addAttribute("countries", CountriesApiPlaceHolderTemp.values());
            return "company/company-create";
        }

        companyService.save(newCompany);

        return "redirect:/companies/list";
    }

    @GetMapping("/activate/{id}")
    public String activateCompanyStatus(@PathVariable ("id") Long companyId){

        companyService.activateCompanyStatus(companyId);

        return "redirect:/companies/list";

    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCompanyStatus(@PathVariable ("id") Long companyId){

        companyService.deactivateCompanyStatus(companyId);

        return "redirect:/companies/list";

    }

    @GetMapping("/update/{id}")
    public String editCompany(@PathVariable ("id") Long companyId, Model model){

        model.addAttribute("company",companyService.findById(companyId));
        model.addAttribute("countries", CountriesApiPlaceHolderTemp.values());

        return "/company/company-update";
    }

    @PostMapping("/update/{id}")
    public String updateCompany(Model model,@Valid @ModelAttribute ("company") CompanyDto company, BindingResult bindingResult){

        bindingResult = companyService.addUpdateTitleValidation(company,bindingResult);

        if (bindingResult.hasFieldErrors()){
            model.addAttribute("countries", CountriesApiPlaceHolderTemp.values());
            return "/company/company-update";
        }

        companyService.update(company);

        return "redirect:/companies/list";

    }




}
