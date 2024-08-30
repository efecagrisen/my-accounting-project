package com.ecs.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDto {

    private Long id;

    @NotNull(message = "This is a required field")
    @Max(value = 100,message = "Quantity cannot be greater than 100")
    @Min(value = 1,message = "Quantity cannot be less than 1")
    private Integer quantity;

    @NotNull(message = "This is a required field")
    @Min(value = 1,message = "Price cannot be less than $1")
    private BigDecimal price;

    @NotNull(message = "Tax is a required field.")
    @Min(value = 0, message = "Tax should be between 0% and 20%.")
    @Max(value = 20, message = "Tax should be between 0% and 20%.")
    private Integer tax;

    private BigDecimal total;
    private BigDecimal profitLoss;
    private Integer remainingQuantity;


    private InvoiceDto invoice;

    @NotNull(message = "Product is a required field")
    private ProductDto product;

}
