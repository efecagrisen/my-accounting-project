package com.ecs.service;

import com.ecs.dto.ClientVendorDto;
import com.ecs.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDto findById(Long id);

    List<ClientVendorDto> listCompanyClientVendors();

    List<ClientVendorDto> listCompanyClientVendorsByType(ClientVendorType clientVendorType);

//    List<ClientVendorDto> listAll();

    void save(ClientVendorDto clientVendorDto);

    void deleteById(Long id);

}
