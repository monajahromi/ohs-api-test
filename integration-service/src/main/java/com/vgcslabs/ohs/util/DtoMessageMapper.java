package com.vgcslabs.ohs.util;

import com.google.protobuf.StringValue;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import com.vgcslabs.order.CreateOrderRequest;
import com.vgcslabs.order.OrderStatus;
import com.vgcslabs.order.Product;
import com.vgcslabs.user.CreateUserRequest;
import com.vgcslabs.user.PaymentMethod;
import com.vgcslabs.user.ShippingAddress;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;

public class DtoMessageMapper {

    public static void main(String[] args) {
        var a = new OrderIntegrationDto() ;
        a.setEmail("dddd");
        toCreateUserRequest(a);
    }
    public static CreateUserRequest toCreateUserRequest(OrderIntegrationDto dto) {
        if (dto == null) {
            return null;
        }

        // Build the ShippingAddress message
        ShippingAddress.Builder shippingAddressBuilder = ShippingAddress.newBuilder();
        if (dto.getShippingAddress() != null) {
            shippingAddressBuilder.setAddress(StringValue.of(dto.getShippingAddress()));
        }
        if (dto.getCountry() != null) {
            shippingAddressBuilder.setCountry(StringValue.of(dto.getCountry()));
        }
        ShippingAddress shippingAddress = shippingAddressBuilder.build();

        PaymentMethod.Builder paymentMethodBuilder = PaymentMethod.newBuilder();
        if (dto.getCreditCardNumber() != null) {
            paymentMethodBuilder.setCreditCardNumber(StringValue.of(dto.getCreditCardNumber()));
        }
        if (dto.getCreditCardType() != null) {
            paymentMethodBuilder.setCreditCardType(StringValue.of(dto.getCreditCardType()));
        }
        PaymentMethod paymentMethod = paymentMethodBuilder.build();

        // Build the PaymentMethod message
        CreateUserRequest.Builder requestBuilder = CreateUserRequest.newBuilder()
                .setEmail(dto.getEmail())
                .addAllPaymentMethods(Collections.singletonList(paymentMethod));

        if (dto.getFullName() != null) {
            requestBuilder.setFullName(StringValue.of(dto.getFullName()));
        }
        if (shippingAddress != null) {
            requestBuilder.setAddress(shippingAddress);
        }


        return requestBuilder.build();
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
        CreateOrderRequest.Builder requestBuilder = CreateOrderRequest.newBuilder()
                .addProducts(product)
                .setStatus(orderStatus)
                .setQuantity(dto.getQuantity());

        if (dto.getDateCreated() != null) {
            requestBuilder.setDateCreated(StringValue.of(formatDate(dto.getDateCreated())));
        }

        return requestBuilder.build();
    }


    private static String formatDate(String dateStr) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
            return dateTime.toLocalDate().format(DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format:" + dateStr, e);
        }
    }

}