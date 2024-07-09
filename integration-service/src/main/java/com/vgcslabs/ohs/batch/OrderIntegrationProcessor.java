package com.vgcslabs.ohs.batch;


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
public class OrderIntegrationProcessor implements ItemProcessor<OrderIntegrationDto, String> {


    private final ProductService productService;
    private final SupplierService supplierService;
    private final UserService userService;
    private final OrderService orderService;

    @Override
    public String process(OrderIntegrationDto integrationDto) {

        System.out.println("In processor, " + integrationDto.getId());
        ProductResponse productResponse = productService.validateProduct(integrationDto.getProductPid());
        System.out.println("product, " + productResponse.getName());
        var sp = supplierService.validateSupplier(integrationDto.getSupplierPid());
        System.out.println("supplier, " + sp.getName());
        UserResponse userResponse = processUser(integrationDto);
        System.out.println("user, " + userResponse.getEmail());
        System.out.println("user, " + userResponse.getPid());
        CreateOrderRequest createOrderRequest = EntityMessageMapper.toCreateOrderRequest(integrationDto);

        createOrderRequest.toBuilder().setUserPid(userResponse.getPid());


        OrderResponse orderResponse = orderService.createOrder(createOrderRequest);
        System.out.println("Order res, " + orderResponse.getPid());
        return orderResponse.getPid();
        //check for validation of supplier
        // process user
        // insert order

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