package com.mapper;

import com.dto.UserRequest;
import com.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "username", source = "name")
    User toUser(UserRequest userRequest);

    @Mapping(target = "name", source = "username")
    UserRequest toUserRequest(User user);
}
