package com.ecs.dto;
import jakarta.validation.constraints.NotBlank;
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
public class AddressDto {

    private Long id;

    @NotBlank(message = "Address is a required field.")
    @Size(max = 100,min = 2,message = "Address should have 2-50 characters long.")
    private String addressLine1;

    @Size(max = 100,message = "Address should have 2-50 characters long.")
    private String addressLine2;

    @NotBlank(message = "City is a required field.")
    @Size(min = 2, max = 50, message = "City should have 2-50 characters long.")
    private String city;

    @NotBlank(message = "State is a required field.")
    @Size(min = 2, max = 50, message = "State should have 2-50 characters long.")
    private String state;

    @NotBlank(message = "Country is a required field.")
    @Size(min = 2, max = 50, message = "Country should have 2-50 characters long.")
    private String country;

    @NotBlank(message = "Zip Code is a required field.")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Please enter a valid zipcode. It should be in the format of 5 digits, optionally followed by a hyphen and 4 more digits (e.g., 12345 or 12345-6789).")
    private String zipCode;

}
