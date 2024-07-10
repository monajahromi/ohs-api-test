package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import com.vgcslabs.ohs.client.OrderClient;
import com.vgcslabs.ohs.client.ProductClient;
import com.vgcslabs.ohs.client.SupplierClient;
import com.vgcslabs.ohs.client.UserClient;
import com.vgcslabs.ohs.util.DtoMessageMapper;
import com.vgcslabs.order.OrderResponse;
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
        System.out.println("In processor, " + integrationDto.getId());
        var productResponse = productClient.validateProduct(integrationDto.getProductPid());
        System.out.println("product, " + productResponse.getName());
        var supplierResponse = supplierService.validateSupplier(integrationDto.getSupplierPid());
        System.out.println("supplier, " + supplierResponse.getName());
        var userResponse = processUser(integrationDto);
        System.out.println("user, " + userResponse.getEmail());
        System.out.println("user, " + userResponse.getPid());
        var createOrderRequest = DtoMessageMapper.toCreateOrderRequest(integrationDto);

        createOrderRequest.toBuilder().setUserPid(userResponse.getPid());


        OrderResponse orderResponse = orderClient.createOrder(createOrderRequest);
        System.out.println("Order res, " + orderResponse.getPid());
        var responseDto = new OrderBatchJobResponseDto();
        responseDto.setUserPid(userResponse.getPid());
        responseDto.setOrderId(integrationDto.getOrderId());
        responseDto.setSupplierPid(supplierResponse.getPid());
        return responseDto;

    }

    public UserResponse processUser(OrderIntegrationDto orderIntegrationDto) {
        UserResponse userResponse = userService.findUserByEmail(orderIntegrationDto.getEmail());
        if (userResponse != null) {
            System.out.println("user Existed, " + userResponse.getEmail());
            return userResponse;
        }
        System.out.println(" user Created, " + userResponse.getEmail());
        return userService.createUser(DtoMessageMapper.toCreateUserRequest(orderIntegrationDto));


    }


}