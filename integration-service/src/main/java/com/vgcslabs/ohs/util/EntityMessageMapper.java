package com.vgcslabs.ohs.util;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import com.vgcslabs.ohs.dto.UserDto;
import com.vgcslabs.order.CreateOrderRequest;
import com.vgcslabs.order.OrderStatus;
import com.vgcslabs.order.Product;
import com.vgcslabs.user.CreateUserRequest;
import com.vgcslabs.user.PaymentMethod;
import com.vgcslabs.user.ShippingAddress;
import com.vgcslabs.user.UserUpdateRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;

public class EntityMessageMapper {

    public static CreateUserRequest userDtoToCreateUserRequest(UserDto user) {
        return CreateUserRequest.newBuilder()
                .setFullName(StringValue.newBuilder().setValue(user.getFullName()).build())
                .setPassword(StringValue.newBuilder().setValue(user.getPassword()).build())
                .setEmail(user.getEmail())
                .build();
    }



    public static CreateUserRequest toCreateUserRequest(OrderIntegrationDto dto) {
        if (dto == null) {
            return null;
        }

        // Build the ShippingAddress message
        ShippingAddress shippingAddress = ShippingAddress.newBuilder()
                .setAddress(StringValue.of(dto.getShippingAddress()))
                .setCountry(StringValue.of(dto.getCountry()))
                .build();

        // Build the PaymentMethod message
        PaymentMethod paymentMethod = PaymentMethod.newBuilder()
                .setCreditCardNumber(StringValue.of(dto.getCreditCardNumber()))
                .setCreditCardType(StringValue.of(dto.getCreditCardType()))
                .build();

        // Build the CreateUserRequest message
        return CreateUserRequest.newBuilder()
                .setFullName(StringValue.of(dto.getFullName()))
                .setEmail(dto.getEmail())
                .setAddress(shippingAddress)
                .addAllPaymentMethods(Collections.singletonList(paymentMethod))
               // .setPassword(StringValue.of(dto.get))
                .build();
    }


    public static CreateOrderRequest toCreateOrderRequest(OrderIntegrationDto dto) {
        if (dto == null) {
            return null;
        }

        // Build the Product message
        Product product = Product.newBuilder()
                .setPid(dto.getProductPid())
                .build();

        // Determine order status
        OrderStatus orderStatus;
        switch (dto.getOrderStatus()) {
            case 0:
                orderStatus = OrderStatus.CREATED;
                break;
            case 1:
                orderStatus = OrderStatus.SHIPPED;
                break;
            case 2:
            default:
                orderStatus = OrderStatus.DELIVERED;
                break;
        }

        // Build the CreateOrderRequest message
        return CreateOrderRequest.newBuilder()
                .addProducts(product)
                .setDateCreated(StringValue.of(formatDate(dto.getDateCreated())))
                .setStatus(orderStatus)
                .setQuantity(Integer.parseInt(dto.getQuantity()))
                .build();
    }


    private static String formatDate(String dateStr) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
            return dateTime.toLocalDate().format(DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

}