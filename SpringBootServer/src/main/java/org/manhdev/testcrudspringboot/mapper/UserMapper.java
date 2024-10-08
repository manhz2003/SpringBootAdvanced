package org.manhdev.testcrudspringboot.mapper;

import org.manhdev.testcrudspringboot.dto.request.UpdateUserRequest;
import org.manhdev.testcrudspringboot.dto.request.UserCreationRequest;
import org.manhdev.testcrudspringboot.dto.response.UserResponse;
import org.manhdev.testcrudspringboot.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
