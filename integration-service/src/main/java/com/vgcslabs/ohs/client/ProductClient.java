package com.vgcslabs.ohs.client;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.config.GrpcClientConfig;
import com.vgcslabs.ohs.exception.NotFoundException;
import com.vgcslabs.product.ProductResponse;
import com.vgcslabs.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
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
    
    public ProductResponse validateProduct(String productPid) {
        try {
            var request = StringValue.newBuilder().setValue(productPid).build();

            return this.productClient.getProductByPid(request);
        } catch (StatusRuntimeException e) {
            System.out.println("productPid not valid, " + e.getMessage());
            if (e.getStatus().getCode().equals(Status.Code.UNKNOWN)) {
                throw new NotFoundException(e.getMessage());
            } else {
                throw new RuntimeException(e.getMessage());
            }

        }
    }


}
