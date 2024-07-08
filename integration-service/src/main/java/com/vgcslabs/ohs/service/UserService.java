package com.vgcslabs.ohs.service;

import com.google.protobuf.StringValue;
import com.vgcslabs.user.CreateUserRequest;
import com.vgcslabs.user.UserResponse;
import com.vgcslabs.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userClient;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        return this.userClient.createUser( createUserRequest);
    }


    public UserResponse getUser(String userPid) {
        var request = StringValue.newBuilder().setValue(userPid).build();
        return this.userClient.getUserByPid( request);
    }

}
