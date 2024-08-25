package com.ecs.dto;

import com.ecs.entity.Address;
import com.ecs.enums.CompanyStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    @NotBlank(message = "Title is a required field.")
    @Size(max = 100,min = 2, message = "Title should be 2-100 characters long.")
    private String title;

    @NotBlank(message = "Title is a required field.")
    @Pattern(regexp = "(\\+\\d{1,3}( )?)?\\(?(\\d{3})\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$", message = "Phone number is required field and may be in any valid phone number format.")
    private String phone;

    @NotBlank(message = "Title is a required field.")
    @Pattern(regexp = "^https?://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*", message = "Website should have a valid format.")
    private String website;

    @Valid
    private AddressDto address;

    private CompanyStatus companyStatus;

    private LocalDateTime insertDateTime;
}
