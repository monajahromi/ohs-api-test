package com.vgcslabs.ohs.service;

import com.google.protobuf.StringValue;
import com.vgcslabs.product.ProductResponse;
import com.vgcslabs.product.ProductServiceGrpc;
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
