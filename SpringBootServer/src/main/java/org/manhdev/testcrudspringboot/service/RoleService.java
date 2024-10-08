package org.manhdev.testcrudspringboot.service;

import java.util.HashSet;
import java.util.List;

import org.manhdev.testcrudspringboot.dto.request.RoleRequest;
import org.manhdev.testcrudspringboot.dto.response.RoleResponse;
import org.manhdev.testcrudspringboot.mapper.RoleMapper;
import org.manhdev.testcrudspringboot.repository.PermissionRepository;
import org.manhdev.testcrudspringboot.repository.RoleRepository;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
