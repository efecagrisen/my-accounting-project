package com.ecs.service.impl;

import com.ecs.dto.RoleDto;
import com.ecs.mapper.MapperUtil;
import com.ecs.repository.RoleRepository;
import com.ecs.service.RoleService;
import com.ecs.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
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

    @Override
    public List<RoleDto> listRolesByLoggedInUserId(Long id) {

        if (id==1L){
            return Collections.singletonList(mapperUtil.convert(roleRepository.findById(2L).get(),RoleDto.class));
        }else {
            return roleRepository.getRolesByIdIsNot(1L)
                    .stream()
                    .map(role -> mapperUtil.convert(role,RoleDto.class))
                    .collect(Collectors.toList());
        }
    }


}
