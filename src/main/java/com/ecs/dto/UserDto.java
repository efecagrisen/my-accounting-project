package com.ecs.dto;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String phone;
    private boolean enabled;
    private RoleDto roleDto;
    private CompanyDto companyDto;
    private boolean isOnlyAdmin;
}
