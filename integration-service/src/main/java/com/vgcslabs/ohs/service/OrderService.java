package com.vgcslabs.ohs.service;

import com.vgcslabs.ohs.config.GrpcClientConfig;
import com.vgcslabs.order.CreateOrderRequest;
import com.vgcslabs.order.OrderResponse;
import com.vgcslabs.order.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service

public class OrderService {

    private final OrderServiceGrpc.OrderServiceBlockingStub orderClient;
    public OrderService(GrpcClientConfig clientConfig) {

        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget(clientConfig.getOrderAddress())
                .usePlaintext()
                .build();
        this.orderClient = OrderServiceGrpc.newBlockingStub(managedChannel);

    }


    public OrderResponse createOrder(CreateOrderRequest order) {
        try {
            OrderResponse orderResponse = orderClient.createOrder(order);
            System.out.println("orderResponse, " + orderResponse);
            return orderResponse;
        } catch (StatusRuntimeException e) {
            System.out.println("Create Order Failed, " + e.getMessage());
            throw new RuntimeException("Create Order Failed: " + e.getMessage());
        }
    }

}
