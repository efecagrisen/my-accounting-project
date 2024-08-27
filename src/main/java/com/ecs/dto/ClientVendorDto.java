package com.ecs.dto;

import com.ecs.enums.ClientVendorType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorDto {

    private Long id;

    @NotBlank(message = "This is a required field.")
    @Size(max = 100,min = 2, message = "Should be 2-100 characters long.")
    private String clientVendorName;

    @Pattern(regexp = "^\\+\\d{1,4}\\s\\(\\d{1,}\\)\\s\\d{1,}-\\d{1,}$", message = "Phone Number is required field and may be in any valid phone number format. +CC (AAA) NNNN-NNNN Ex: +1 (222) 333-4444")
    private String phone;

    @NotBlank(message = "This is a required field.")
    @Pattern(regexp = "^https?://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*", message = "Website should have a valid format.")
    private String website;

    @NotNull(message = "Please select type")
    private ClientVendorType clientVendorType;

    @Valid
    private AddressDto address;

    @Valid
    private CompanyDto company;

    private boolean hasInvoice;


}
