package com.vgcslabs.ohs.client;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.config.GrpcClientConfig;
import com.vgcslabs.supplier.SupplierServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
    public void validateSupplier(String supplierPid) {
        try {
             supplierClient.getSupplierByPid(StringValue.newBuilder().setValue(supplierPid).build());
        } catch (StatusRuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
