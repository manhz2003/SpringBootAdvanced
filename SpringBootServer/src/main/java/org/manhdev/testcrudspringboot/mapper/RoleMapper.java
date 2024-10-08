package org.manhdev.testcrudspringboot.mapper;

import org.manhdev.testcrudspringboot.dto.request.RoleRequest;
import org.manhdev.testcrudspringboot.dto.response.RoleResponse;
import org.manhdev.testcrudspringboot.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
