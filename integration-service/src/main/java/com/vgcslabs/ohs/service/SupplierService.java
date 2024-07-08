package com.vgcslabs.ohs.service;

import com.google.protobuf.StringValue;
import com.vgcslabs.supplier.SupplierResponse;
import com.vgcslabs.supplier.SupplierServiceGrpc;
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
