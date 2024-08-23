package com.ecs.service;

import com.ecs.dto.CompanyDto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getCompanyDtoByLoggedInUser();

    List<CompanyDto> listAllCompanies();

    CompanyDto findCompanyOfLoggedInUser();

    List<CompanyDto> findCompaniesOfNonRootUsers(Long id);

    CompanyDto findById(Long id);

    void save(CompanyDto companyDto);

    void update(CompanyDto companyDto);

    void activateCompanyStatus(Long companyId);

    void deactivateCompanyStatus(Long companyId);

    BindingResult addTitleValidation(String title,BindingResult bindingResult);

}
