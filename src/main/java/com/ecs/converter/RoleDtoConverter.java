package com.ecs.converter;

import com.ecs.dto.RoleDto;
import com.ecs.service.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter implements Converter<String, RoleDto> {

    private final RoleService roleService;

    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }


    @Override
    public RoleDto convert(String source) {

        if (source==null || source.equals("")) {
            return null;
        }

        return roleService.findById(Long.parseLong(source));
    }
}
