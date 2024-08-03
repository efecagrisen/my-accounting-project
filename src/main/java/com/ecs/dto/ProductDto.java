package com.ecs.dto;

import com.ecs.enums.ProductUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private Integer quantityInStock;
    private Integer lowLimitAlert;
    private ProductUnit productUnit;
    private CategoryDto categoryDto;
    private boolean hasProduct;

}
