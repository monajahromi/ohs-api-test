package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.client.OrderClient;
import com.vgcslabs.ohs.client.ProductClient;
import com.vgcslabs.ohs.client.SupplierClient;
import com.vgcslabs.ohs.client.UserClient;
import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import com.vgcslabs.ohs.util.DtoMessageMapper;
import com.vgcslabs.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderIntegrationProcessor implements ItemProcessor<OrderIntegrationDto, OrderBatchJobResponseDto> {


    private final ProductClient productClient;
    private final SupplierClient supplierService;
    private final UserClient userService;
    private final OrderClient orderClient;

    @Override
    public OrderBatchJobResponseDto process(OrderIntegrationDto integrationDto) {
        productClient.validateProduct(integrationDto.getProductPid());
        supplierService.validateSupplier(integrationDto.getSupplierPid());
        var user = processUser(integrationDto);

        var createOrderRequest = DtoMessageMapper.toCreateOrderRequest(integrationDto);
        createOrderRequest.toBuilder().setUserPid(user.getPid());
        orderClient.createOrder(createOrderRequest);

        var responseDto = new OrderBatchJobResponseDto();
        responseDto.setUserPid(user.getPid());
        responseDto.setOrderId(integrationDto.getOrderId());
        responseDto.setSupplierPid(integrationDto.getSupplierPid());
       return responseDto;

    }

    public UserResponse processUser(OrderIntegrationDto orderIntegrationDto) {
        UserResponse userResponse = userService.findUserByEmail(orderIntegrationDto.getEmail());
        if (userResponse != null) {
            return userResponse;
        }
        return userService.createUser(DtoMessageMapper.toCreateUserRequest(orderIntegrationDto));
    }


}