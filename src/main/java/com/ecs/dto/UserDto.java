package com.ecs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String phone;
    private boolean enabled;
    private RoleDto role;
    private CompanyDto company;
    private boolean isOnlyAdmin;
}
