package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.dto.OrderIntegrationData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.vgcslabs.ohs.batch.BatchJobConstants.ORDER_INTEGRATION_JSON_ITEM_WRITER;

@Component
@RequiredArgsConstructor
public class OrderIntegrationDataWriter {

    private final BatchJobProperties jobProperties;

    @Bean("orderIntegrationJsonItemWriter")
    public JsonFileItemWriter<OrderIntegrationData> jsonFileItemWriter() {
        return new JsonFileItemWriterBuilder<OrderIntegrationData>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(jobProperties.getOrderIntegrationOutputFile()))
                .name(ORDER_INTEGRATION_JSON_ITEM_WRITER)
                .build();
    }

}