package com.vgcslabs.ohs.util;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.dto.UserDto;
import com.vgcslabs.user.CreateUserRequest;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EntityMessageMapper {

    public static CreateUserRequest userDtoToCreateUserRequest(UserDto user) {
        return CreateUserRequest.newBuilder()
                .setFullName(StringValue.newBuilder().setValue(user.getFullName()).build())
                .setPassword(StringValue.newBuilder().setValue(user.getPassword()).build())
                .setEmail(user.getEmail())
                .build();
    }



}