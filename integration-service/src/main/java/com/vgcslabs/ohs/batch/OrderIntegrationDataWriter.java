package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.config.BatchJobProperties;
import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.vgcslabs.ohs.batch.BatchJobConstants.ORDER_INTEGRATION_JSON_ITEM_WRITER;

@Component
@RequiredArgsConstructor
public class OrderIntegrationDataWriter {

    private final BatchJobProperties jobProperties;

    @Bean("orderIntegrationJsonItemWriterSuccess")
    public ItemStreamWriter<OrderBatchJobResponseDto> jsonFileItemWriterSuccess() {

      return  new JsonFileItemWriterBuilder<OrderBatchJobResponseDto>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(jobProperties.getOrderIntegrationOutputSucessFile()))
                .name(ORDER_INTEGRATION_JSON_ITEM_WRITER)
                .build();
    }
    @Bean("orderIntegrationJsonItemWriterFail")
    public ItemStreamWriter<OrderBatchJobResponseDto> jsonFileItemWriterFail() {
       return new JsonFileItemWriterBuilder<OrderBatchJobResponseDto>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(jobProperties.getOrderIntegrationOutputFailFile()))
                .name(ORDER_INTEGRATION_JSON_ITEM_WRITER)
                .build();

    }


}