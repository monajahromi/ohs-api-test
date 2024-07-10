package com.vgcslabs.ohs.client;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.config.GrpcClientConfig;
import com.vgcslabs.product.ProductResponse;
import com.vgcslabs.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class ProductClient {
    private final ProductServiceGrpc.ProductServiceBlockingStub productClient;

    public ProductClient(GrpcClientConfig clientConfig) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget(clientConfig.getProductAddress())
                .usePlaintext()
                .build();
        this.productClient = ProductServiceGrpc.newBlockingStub(managedChannel);

    }

    public void validateProduct(String productPid) {
        try {
             this.productClient.getProductByPid(StringValue.newBuilder().setValue(productPid).build());
        } catch (StatusRuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
