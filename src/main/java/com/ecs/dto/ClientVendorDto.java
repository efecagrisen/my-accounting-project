package com.ecs.dto;

import com.ecs.enums.ClientVendorType;

public class ClientVendorDto {

    private Long id;
    private String clientVendorName;
    private String phone;
    private String website;
    private ClientVendorType clientVendorType;
    private AddressDto address;
    private CompanyDto company;
    private boolean hasInvoice;


}
