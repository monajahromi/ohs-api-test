package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.client.OrderClient;
import com.vgcslabs.ohs.client.ProductClient;
import com.vgcslabs.ohs.client.SupplierClient;
import com.vgcslabs.ohs.client.UserClient;
import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import com.vgcslabs.user.UserResponse;
import com.vgcslabs.user.UserResponseOrBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class OrderIntegrationProcessorTest {

    @Mock
    private ProductClient productClient;

    @Mock
    private SupplierClient supplierClient;

    @Mock
    private UserClient userClient;

    @Mock
    private OrderClient orderClient;

    private OrderIntegrationProcessor processor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        processor = new OrderIntegrationProcessor(productClient, supplierClient, userClient, orderClient);
    }

    @Test
    void testProcess() {
        OrderIntegrationDto integrationDto = new OrderIntegrationDto();
        String userId = "d007453456bb";
        String orderId = "order789";
        String supplierId = "supplier456";

        integrationDto.setProductPid("product123");
        integrationDto.setEmail("test@example.com");
        integrationDto.setSupplierPid(supplierId);
        integrationDto.setOrderId(orderId);
        integrationDto.setOrderStatus(1);
        integrationDto.setQuantity(1);

        UserResponse mockUserResponse = UserResponse.newBuilder().setPid(userId).build();


        doNothing().when(productClient).validateProduct(integrationDto.getProductPid());
        doNothing().when(supplierClient).validateSupplier(integrationDto.getSupplierPid());
        when(userClient.findUserByEmail(integrationDto.getEmail())).thenReturn(null);
        when(userClient.createUser(any())).thenReturn(mockUserResponse);
        when(orderClient.createOrder(any())).thenReturn(null);

        OrderBatchJobResponseDto responseDto = processor.process(integrationDto);

        verify(productClient, times(1)).validateProduct("product123");
        verify(supplierClient, times(1)).validateSupplier("supplier456");
        verify(userClient, times(1)).findUserByEmail("test@example.com");
        verify(userClient, times(1)).createUser(any());
        verify(orderClient, times(1)).createOrder(any());

        assertEquals(userId, responseDto.getUserPid());
        assertEquals(orderId, responseDto.getOrderId());
        assertEquals(supplierId, responseDto.getSupplierPid());
    }




}