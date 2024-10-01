package com.ecs.service;

import com.ecs.dto.CompanyDto;
import com.ecs.dto.CountriesDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getCompanyDtoByLoggedInUser();

    List<CompanyDto> listAllCompanies();

    List<CompanyDto> listNonRootCompanies();

    CompanyDto findCompanyOfLoggedInUser();

    List<CompanyDto> findCompaniesOfNonRootUsers(Long id);

    CompanyDto findById(Long id);

    void save(CompanyDto companyDto);

    CompanyDto update(CompanyDto companyDto);

    void activateCompanyStatus(Long companyId);

    void deactivateCompanyStatus(Long companyId);

    BindingResult addTitleValidation(String title,BindingResult bindingResult);
    BindingResult addUpdateTitleValidation(CompanyDto companyDto,BindingResult bindingResult);

    List<String> getCountries();

}
