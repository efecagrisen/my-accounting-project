package com.ecs.service;

import com.ecs.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getCompanyDtoByLoggedInUser();

    List<CompanyDto> listAllCompanies();

    CompanyDto findCompanyOfLoggedInUser();

    List<CompanyDto> findCompaniesOfNonRootUsers(Long id);


    CompanyDto findById(Long id);

}
