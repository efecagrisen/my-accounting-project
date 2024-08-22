package com.ecs.service.impl;

import com.ecs.dto.UserDto;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.UserRepository;
import com.ecs.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;

    public UserServiceImpl(MapperUtil mapperUtil, UserRepository userRepository) {
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findByUsername(String username) {
        return mapperUtil.convert(userRepository.findByUsername(username),UserDto.class);
    }


}
