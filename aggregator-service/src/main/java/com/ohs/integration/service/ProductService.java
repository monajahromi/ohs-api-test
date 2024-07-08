package com.ohs.integration.service;

import com.google.protobuf.StringValue;
import com.ohs.product.ProductResponse;
import com.ohs.product.ProductServiceGrpc;
import lombok.AllArgsConstructor;
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

}
