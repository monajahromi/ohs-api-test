package com.vgcslabs.ohs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderIntegrationDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String supplierPid;
    private String creditCardNumber;
    private String creditCardType;
    private String orderId;
    private String productPid;
    private String shippingAddress;
    private String country;
    private String dateCreated;
    private String quantity;
    private String fullName;
    private Integer orderStatus;


}
