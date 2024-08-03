package com.ecs.dto;

import com.ecs.enums.ClientVendorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
