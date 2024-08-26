package com.ecs.service.impl;

import com.ecs.dto.CompanyDto;
import com.ecs.dto.RoleDto;
import com.ecs.dto.UserDto;
import com.ecs.entity.User;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.UserRepository;
import com.ecs.service.RoleService;
import com.ecs.service.SecurityService;
import com.ecs.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(MapperUtil mapperUtil, UserRepository userRepository, @Lazy SecurityService securityService, PasswordEncoder passwordEncoder) {
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findByUsername(String username) {
        return mapperUtil.convert(userRepository.findByUsername(username),UserDto.class);
    }

    @Override
    public UserDto findById(Long id) {
        return mapperUtil.convert(userRepository.findById(id), UserDto.class);
    }

    @Override
    public List<UserDto> listAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(dto-> mapperUtil.convert(dto,UserDto.class))
                .collect(Collectors.toList());
    }



    @Override
    public List<UserDto> findUsersByCompanyIdOrderByRoleIdAsc(Long companyId) {

        return userRepository.findUsersByCompanyIdOrderByRoleIdAsc(securityService.getLoggedInUser().getCompany().getId())
                .stream()
                .map(user->mapperUtil.convert(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> listAdminUsers(String roleDescription) {

        return userRepository.findAllByRoleDescriptionOrderByCompany_TitleAsc("Admin")
                .stream()
                .map(user ->  mapperUtil.convert(user,UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void save(UserDto userDto) {
        userDto.setEnabled(true);
        User user = mapperUtil.convert(userDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
