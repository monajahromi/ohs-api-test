package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.dto.OrderIntegrationData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderIntegrationProcessorConfig implements ItemProcessor<OrderIntegrationData, OrderIntegrationData> {

    @Override
    public OrderIntegrationData process(OrderIntegrationData orderIntegrationData) {
        return orderIntegrationData;
    }
}