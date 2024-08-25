package com.ecs.service;

import com.ecs.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto findById(Long id);

    List<RoleDto> listAllRoles();
    List<RoleDto> listAllRolesOtherThanRoot();

    List<RoleDto> listRolesByLoggedInUserId(Long id);






}
