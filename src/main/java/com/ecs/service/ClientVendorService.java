package com.ecs.service;

import com.ecs.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDto findById(Long id);

    List<ClientVendorDto> listCompanyClientVendors();

//    List<ClientVendorDto> listAll();

    void save(ClientVendorDto clientVendorDto);

    void deleteById(Long id);

}
