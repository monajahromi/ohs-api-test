package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import com.vgcslabs.ohs.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderIntegrationProcessor implements ItemProcessor<OrderIntegrationDto, String> {


    private final ProductService productService;
    @Override
    public String process(OrderIntegrationDto orderIntegrationDto) {
        //check for validation of product
       /* if(Integer.valueOf(orderIntegrationDto.getId() )==1){
            productService.validateProduct("kkkkkk");
        }*/
        if (Integer.valueOf(orderIntegrationDto.getId()) % 2 == 0) {
            return "a" ;
        } else {
        throw new RuntimeException();
        }

        //check for validation of supplier
        // process user
        // insert order

    }
}