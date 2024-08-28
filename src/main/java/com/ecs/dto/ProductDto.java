package com.ecs.dto;

import com.ecs.enums.ProductUnit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "This is a required field.")
    @Size(max = 100,min = 2, message = "Should be 2-100 characters long.")
    private String name;

    private Integer quantityInStock;

    @NotNull(message = "Low Limit Alert is a required field.")
    @Min(value = 1, message = "Low Limit Alert should be at least 1.")
    private Integer lowLimitAlert;

    @NotNull(message = "This is a required field.")
    private ProductUnit productUnit;

    @NotNull(message = "This is a required field.")
    private CategoryDto category;


    private boolean hasProduct;

}
