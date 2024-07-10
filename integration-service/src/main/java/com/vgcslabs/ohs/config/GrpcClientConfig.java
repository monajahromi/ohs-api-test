package com.vgcslabs.ohs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GrpcClientConfig {

    @Value("${SUPPLIER_URL}")
    private String supplierAddress;

    @Value("${PRODUCT_URL}")
    private String productAddress;

    @Value("${ORDER_URL}")
    private String orderAddress;

    @Value("${USER_URL}")
    private String userAddress;

}
