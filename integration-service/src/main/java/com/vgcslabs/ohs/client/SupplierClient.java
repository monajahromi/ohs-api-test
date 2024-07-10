package com.vgcslabs.ohs.client;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.config.GrpcClientConfig;
import com.vgcslabs.ohs.exception.NotFoundException;
import com.vgcslabs.supplier.SupplierResponse;
import com.vgcslabs.supplier.SupplierServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class SupplierClient {
    private final SupplierServiceGrpc.SupplierServiceBlockingStub supplierClient;
    public SupplierClient(GrpcClientConfig clientConfig) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget(clientConfig.getSupplierAddress())
                .usePlaintext()
                .build();
        this.supplierClient = SupplierServiceGrpc.newBlockingStub(managedChannel);

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
