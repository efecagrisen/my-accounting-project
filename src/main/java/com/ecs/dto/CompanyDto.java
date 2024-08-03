package com.ecs.dto;

import com.ecs.entity.Address;
import com.ecs.enums.CompanyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {

    private Long id;
    private String title;
    private String phone;
    private String website;
    private AddressDto address;
    private CompanyStatus companyStatus;
}
