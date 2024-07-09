package com.vgcslabs.ohs.service;

import com.vgcslabs.order.CreateOrderRequest;
import com.vgcslabs.order.OrderResponse;
import com.vgcslabs.order.OrderServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service

public class OrderService {
    @GrpcClient("order-service")
    private OrderServiceGrpc.OrderServiceBlockingStub orderClient;

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
