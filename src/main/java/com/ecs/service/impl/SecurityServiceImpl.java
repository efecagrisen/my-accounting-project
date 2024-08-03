package com.ecs.service.impl;

import com.ecs.entity.User;
import com.ecs.entity.common.UserPrincipal;
import com.ecs.repository.UserRepository;
import com.ecs.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user==null){
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);

    }
}
