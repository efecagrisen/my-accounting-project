package com.ecs.service.impl;

import com.ecs.dto.CompanyDto;
import com.ecs.dto.UserDto;
import com.ecs.entity.Company;
import com.ecs.entity.User;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.CompanyRepository;
import com.ecs.repository.UserRepository;
import com.ecs.service.CompanyService;
import com.ecs.service.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final SecurityService securityService;

    public CompanyServiceImpl(MapperUtil mapperUtil, UserRepository userRepository, CompanyRepository companyRepository, SecurityService securityService) {
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.securityService = securityService;
    }

    @Override
    public List<CompanyDto> getCompanyDtoByLoggedInUser() {

        UserDto loggedInUser = securityService.getLoggedInUser();

        if (loggedInUser.getRole().getId() == 1){
            List<Company> companies = companyRepository.getAllCompanyForRootUser(loggedInUser.getCompany().getId());

            return companies.stream()
                    .map(company -> mapperUtil.convert(company, CompanyDto.class))
                    .collect(Collectors.toList());
        } else {

            Company company = companyRepository.getCompanyForCurrent(loggedInUser.getCompany().getId());

            return Collections.singletonList(mapperUtil.convert(company, CompanyDto.class));
        }
    }

    @Override
    public List<CompanyDto> listAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(company -> mapperUtil.convert(company,CompanyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto findCompanyOfLoggedInUser() {
        return mapperUtil.convert(companyRepository.findById(securityService.getLoggedInUser().getCompany().getId()),CompanyDto.class);
    }

    @Override
    public List<CompanyDto> findCompaniesOfNonRootUsers(Long id) {
        return companyRepository.findCompaniesOfNonRootUsers(id)
                .stream()
                .map(company -> mapperUtil.convert(company,CompanyDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public CompanyDto findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id), CompanyDto.class);
    }

}
