package com.ohs.integration.util;

import com.google.protobuf.StringValue;
import com.ohs.integration.dto.UserDto;
import com.ohs.user.CreateUserRequest;

public class EntityMessageMapper {

    public static CreateUserRequest userDtoToCreateUserRequest(UserDto user) {
        return CreateUserRequest.newBuilder()
                .setFullName(StringValue.newBuilder().setValue(user.getFullName()).build())
                .setPassword(StringValue.newBuilder().setValue(user.getPassword()).build())
                .setEmail(user.getEmail())
                .build();
    }

}