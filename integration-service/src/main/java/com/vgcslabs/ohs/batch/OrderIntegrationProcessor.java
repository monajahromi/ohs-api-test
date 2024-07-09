package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import com.vgcslabs.ohs.service.OrderService;
import com.vgcslabs.ohs.service.ProductService;
import com.vgcslabs.ohs.service.SupplierService;
import com.vgcslabs.ohs.service.UserService;
import com.vgcslabs.ohs.util.EntityMessageMapper;
import com.vgcslabs.order.CreateOrderRequest;
import com.vgcslabs.order.OrderResponse;
import com.vgcslabs.product.ProductResponse;
import com.vgcslabs.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderIntegrationProcessor implements ItemProcessor<OrderIntegrationDto, OrderBatchJobResponseDto> {


    private final ProductService productService;
    private final SupplierService supplierService;
    private final UserService userService;
    private final OrderService orderService;

    @Override
    public OrderBatchJobResponseDto process(OrderIntegrationDto integrationDto) {
        System.out.println("In processor, " + integrationDto.getId());
        var productResponse = productService.validateProduct(integrationDto.getProductPid());
        System.out.println("product, " + productResponse.getName());
        var supplierResponse = supplierService.validateSupplier(integrationDto.getSupplierPid());
        System.out.println("supplier, " + supplierResponse.getName());
        var userResponse = processUser(integrationDto);
        System.out.println("user, " + userResponse.getEmail());
        System.out.println("user, " + userResponse.getPid());
        var createOrderRequest = EntityMessageMapper.toCreateOrderRequest(integrationDto);

        createOrderRequest.toBuilder().setUserPid(userResponse.getPid());


        OrderResponse orderResponse = orderService.createOrder(createOrderRequest);
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
        return userService.createUser(EntityMessageMapper.toCreateUserRequest(orderIntegrationDto));


    }


}