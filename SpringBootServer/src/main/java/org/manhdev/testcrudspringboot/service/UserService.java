package org.manhdev.testcrudspringboot.service;

import java.util.HashSet;
import java.util.List;

import org.manhdev.testcrudspringboot.constant.PredefinedRole;
import org.manhdev.testcrudspringboot.dto.request.UpdateUserRequest;
import org.manhdev.testcrudspringboot.dto.request.UserCreationRequest;
import org.manhdev.testcrudspringboot.dto.response.UserResponse;
import org.manhdev.testcrudspringboot.exception.AppException;
import org.manhdev.testcrudspringboot.exception.ErrorCode;
import org.manhdev.testcrudspringboot.mapper.UserMapper;
import org.manhdev.testcrudspringboot.model.Role;
import org.manhdev.testcrudspringboot.model.User;
import org.manhdev.testcrudspringboot.repository.RoleRepository;
import org.manhdev.testcrudspringboot.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_ID_EXISTED));

        return userMapper.toUserResponse(user);
    }

    //    kiểm tra quyền admin thì mới cho thực hiện hàm getUsers, có thể truyền role
    //    hoặc truyền permission cũng đc
    //    hasAuthority giúp truyền được permission vào để kiểm tra quyền
    @PreAuthorize("hasAuthority('GET_DATA_USER')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_ID_EXISTED)));
    }

    //    hasRole nếu truyền perrsion vào sẽ k được vì ta đã cấu hình hàm buildScope thêm tiền
    //    tố ROLE_ vào các role để phân biệt
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new AppException(ErrorCode.PASSWORD_NULL_OR_EMPTY);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_ID_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        if (id == null || id.trim().isEmpty() || "null".equals(id)) {
            throw new AppException(ErrorCode.PASSWORD_NULL_OR_EMPTY);
        }

        UserResponse user = getUserById(id);
        userRepository.deleteById(user.getId());
    }
}
