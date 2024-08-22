package com.ecs.service;

import com.ecs.dto.UserDto;

public interface UserService {

    UserDto findByUsername(String username);

}
