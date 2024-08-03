package com.ecs.service;

import com.ecs.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getCompanyDtoByLoggedInUser();

}
