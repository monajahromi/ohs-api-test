package com.vgcslabs.ohs.service;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.exception.NotFoundException;
import com.vgcslabs.supplier.SupplierResponse;
import com.vgcslabs.supplier.SupplierServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @GrpcClient("supplier-service")
    private SupplierServiceGrpc.SupplierServiceBlockingStub supplierClient;

    public SupplierResponse getSupplierByPid(String supplierPid) {
        var request = StringValue.newBuilder().setValue(supplierPid).build();
        return supplierClient.getSupplierByPid( request);
    }
    public SupplierResponse validateSupplier(String supplierPid) {
        try {
            return supplierClient.getSupplierByPid(StringValue.newBuilder().setValue(supplierPid).build());
        } catch (StatusRuntimeException e) {
            System.out.println("supplierPid not valid, " + e.getMessage());
            if (e.getStatus().getCode().equals(Status.Code.UNKNOWN)) {
                throw new NotFoundException(e.getMessage());
            } else {
                throw new RuntimeException(e.getMessage());
            }

        }
    }

}
