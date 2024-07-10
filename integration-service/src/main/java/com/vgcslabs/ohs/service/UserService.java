package com.vgcslabs.ohs.service;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.config.GrpcClientConfig;
import com.vgcslabs.user.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserServiceGrpc.UserServiceBlockingStub userClient;
    public UserService(GrpcClientConfig clientConfig) {
         ManagedChannel managedChannel = ManagedChannelBuilder.forTarget(clientConfig.getUserAddress())
                .usePlaintext()
                .build();
        this.userClient = UserServiceGrpc.newBlockingStub(managedChannel);

    }

    public UserResponse getUser(String userPid) {
        var request = StringValue.newBuilder().setValue(userPid).build();
        return userClient.getUserByPid(request);
    }

    public UserResponse createUser(CreateUserRequest user) {
        try {
            return userClient.createUser(user);
        } catch (StatusRuntimeException e) {
            System.out.println("Create User Failed, " + e.getMessage());
            throw new RuntimeException("Create User Failed: " + e.getMessage());
        }

    }

    public UserResponse findUserByEmail(String email) {
        try {
            UsersResponse usersResponse = userClient.search(UserSearchFilter
                    .newBuilder()
                    .setEmail(StringValue.newBuilder().setValue(email).build())
                    .build());

            if (usersResponse != null && usersResponse.getUsersList().size() > 0)
                return usersResponse.getUsers(0);

        } catch (StatusRuntimeException e) {
            System.out.println("Existence User Failed" + e.getMessage());
            return null;

        }
        return null;

    }


}
