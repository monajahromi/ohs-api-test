package com.ohs.integration.service;

import com.google.protobuf.StringValue;
import com.ohs.product.ProductResponse;
import com.ohs.product.ProductServiceGrpc;
import com.ohs.supplier.SupplierResponse;
import com.ohs.supplier.SupplierServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @GrpcClient("supplier-service")
    private SupplierServiceGrpc.SupplierServiceBlockingStub supplierClient;

    public SupplierResponse getSupplierByPid(String supplierPid) {
        var request = StringValue.newBuilder().setValue(supplierPid).build();
        return this.supplierClient.getSupplierByPid( request);
    }

}
