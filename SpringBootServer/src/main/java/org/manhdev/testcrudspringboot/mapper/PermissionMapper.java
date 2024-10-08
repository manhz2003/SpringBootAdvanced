package org.manhdev.testcrudspringboot.mapper;

import org.manhdev.testcrudspringboot.dto.request.PermissionRequest;
import org.manhdev.testcrudspringboot.dto.response.PermissionResponse;
import org.manhdev.testcrudspringboot.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
