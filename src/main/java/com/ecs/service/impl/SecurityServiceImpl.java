package com.ecs.service.impl;

import com.ecs.dto.UserDto;
import com.ecs.entity.User;
import com.ecs.entity.common.UserPrincipal;
import com.ecs.repository.UserRepository;
import com.ecs.service.SecurityService;
import com.ecs.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserService userService;

    public SecurityServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user==null){
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);

    }

    @Override
    public UserDto getLoggedInUser() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.findByUsername(username);
    }

    @Override
    public Long getLoggedInUserCompanyId() {
        return getLoggedInUser().getCompany().getId();
    }


}
