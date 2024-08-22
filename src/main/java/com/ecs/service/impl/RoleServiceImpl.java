package com.ecs.service.impl;

import com.ecs.dto.RoleDto;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.RoleRepository;
import com.ecs.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public RoleDto findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id).get(), RoleDto.class);
    }

    @Override
    public List<RoleDto> listAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> mapperUtil.convert(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> listAllRolesOtherThanRoot() {
        return roleRepository.getRolesByIdIsNot(1L)
                .stream()
                .map(role -> mapperUtil.convert(role,RoleDto.class))
                .collect(Collectors.toList());
    }


}
