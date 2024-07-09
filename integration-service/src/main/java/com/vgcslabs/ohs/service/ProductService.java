package com.vgcslabs.ohs.service;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.exception.NotFoundException;
import com.vgcslabs.product.ProductResponse;
import com.vgcslabs.product.ProductServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service

public class ProductService {


    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productClient;

    public ProductResponse getProduct(String productPid) {
        var request = StringValue.newBuilder().setValue(productPid).build();
        return this.productClient.getProductByPid(request);
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
