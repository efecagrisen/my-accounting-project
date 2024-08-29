package com.ecs.service.impl;

import com.ecs.dto.ClientVendorDto;
import com.ecs.entity.ClientVendor;
import com.ecs.enums.ClientVendorType;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.ClientVendorRepository;
import com.ecs.service.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final MapperUtil mapperUtil;
    private final ClientVendorRepository clientVendorRepository;
    private final UserService userService;
    private final CompanyService companyService;
    private final RoleService roleService;
    private final SecurityService securityService;
    private final InvoiceService invoiceService;

    public ClientVendorServiceImpl(MapperUtil mapperUtil, ClientVendorRepository clientVendorRepository, UserService userService, CompanyService companyService, RoleService roleService, SecurityService securityService, InvoiceService invoiceService) {
        this.mapperUtil = mapperUtil;
        this.clientVendorRepository = clientVendorRepository;
        this.userService = userService;
        this.companyService = companyService;
        this.roleService = roleService;
        this.securityService = securityService;
        this.invoiceService = invoiceService;
    }


    @Override
    public ClientVendorDto findById(Long id) {
        return mapperUtil.convert(clientVendorRepository.findById(id).get(), ClientVendorDto.class);
    }

    @Override
    public List<ClientVendorDto> listCompanyClientVendors() {

        Long loggedInUserCompanyId = securityService.getLoggedInUser().getCompany().getId();

        return clientVendorRepository.getAllByCompanyIdOrderByClientVendorTypeAscClientVendorName(loggedInUserCompanyId)
                .stream()
                .map(clientVendor -> mapperUtil.convert(clientVendor, ClientVendorDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<ClientVendorDto> listCompanyClientVendorsByType(ClientVendorType clientVendorType) {
        return listCompanyClientVendors().stream()
                .filter(p->p.getClientVendorType().equals(clientVendorType))
                .collect(Collectors.toList());
    }


//    @Override
//    public List<ClientVendorDto> listAll() {
//        return clientVendorRepository.findAll()
//                .stream()
//                .map(clientVendor -> mapperUtil.convert(clientVendor, ClientVendorDto.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {
        clientVendorDto.setCompany(companyService.findCompanyOfLoggedInUser());
        clientVendorRepository.save(mapperUtil.convert(clientVendorDto, ClientVendor.class));
    }

    @Override
    public void deleteById(Long id) {

        ClientVendor clientVendorToBeDeleted = clientVendorRepository.findById(id).get();
        clientVendorToBeDeleted.setIsDeleted(true);
        clientVendorRepository.save(clientVendorToBeDeleted);
    }

    @Override
    public BindingResult checkClientVendorNameExistsByType(String clientVendorName,ClientVendorType clientVendorType, BindingResult bindingResult) {

            if (clientVendorRepository.existsClientVendorByClientVendorNameAndClientVendorType(clientVendorName,clientVendorType)){
                bindingResult.addError(new FieldError("newClientVendor","clientVendorName","This name already exists"));
            }
        return bindingResult;
    }

}
