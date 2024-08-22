package com.ecs.service;

import com.ecs.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

    UserDto getLoggedInUser();

    Long getLoggedInUserCompanyId();

}
