package com.ecs.service;

import com.ecs.dto.RoleDto;
import com.ecs.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);
    UserDto findById(Long id);


    List<UserDto> listAllUsers();
    List<UserDto> listAdminUsers(String roleDescription);

    List<UserDto> findUsersByCompanyIdOrderByRoleIdAsc(Long companyId);

    void save(UserDto userDto);

    void delete(UserDto userDto);


}
