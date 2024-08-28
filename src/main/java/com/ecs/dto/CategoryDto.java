package com.ecs.dto;
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
public class CategoryDto {

    private Long id;

    @NotNull
    @NotBlank(message = "This is a required field.")
    @Size(max = 50,min = 2, message = "Should be 2-50 characters long.")
    private String description;

    private CompanyDto companyDto;

    private boolean hasProduct;

}
