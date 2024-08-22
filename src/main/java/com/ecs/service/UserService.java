package com.ecs.service;

import com.ecs.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);
    UserDto findById(Long id);


    List<UserDto> listAllUsers();

    List<UserDto> listUsersByCompanyId(Long companyId);

    void save(UserDto userDto);

    void delete(UserDto userDto);


}
