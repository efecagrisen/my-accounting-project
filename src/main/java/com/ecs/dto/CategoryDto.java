package com.ecs.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private Long id;
    private String description;
    private CompanyDto companyDto;
    private boolean hasProduct;

}
