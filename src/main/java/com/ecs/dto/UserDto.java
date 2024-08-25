package com.ecs.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "This is a required field.")
    @NotNull(message = "Username is a required field.")
    @Email(message = "A user with this email already exists. Please try with different email.")
    private String username;

    @NotBlank(message = "Password is a required field.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}", message = "Password should be at least 4 characters long and needs to contain 1 capital letter, 1 small letter and 1 special character or number.")
    private String password;

    @NotNull(message = "Passwords should match.")
    private String confirmPassword;

    @NotBlank(message = "This is a required field")
    @Size(min = 2, max = 20, message = "First Name must be between 2 and 50 characters long.")
    private String firstname;

    @NotBlank(message = "This is a required field")
    @Size(min = 2, max = 20, message = "Last Name must be between 2 and 50 characters long.")
    private String lastname;

    @Pattern(regexp = "^\\+\\d{1,4}\\s\\(\\d{1,}\\)\\s\\d{1,}-\\d{1,}$", message = "Phone Number is required field and may be in any valid phone number format. +CC (AAA) NNNN-NNNN Ex: +1 (222) 333-4444")
    private String phone;

    private boolean enabled;

    @NotNull(message = "Please select a Role.")
    private RoleDto role;

    @NotNull(message = "Please select a Company.")
    private CompanyDto company;

    private boolean isOnlyAdmin;


    public void setPassword(String password) {
        this.password = password;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if (this.password == null || this.confirmPassword == null) {
            return;
        } else if (!this.password.equals(this.confirmPassword)) {
            this.confirmPassword = null;
        }
    }
}
