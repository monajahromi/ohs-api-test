package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
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

    @Bean("orderIntegrationJsonItemWriterSuccess")
    public ItemStreamWriter<String> jsonFileItemWriterSuccess() {

      return  new JsonFileItemWriterBuilder<String>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(jobProperties.getOrderIntegrationOutputSucessFile()))
                .name(ORDER_INTEGRATION_JSON_ITEM_WRITER)
                .build();
    }
    @Bean("orderIntegrationJsonItemWriterFail")
    public ItemStreamWriter<String> jsonFileItemWriterFail() {
       return new JsonFileItemWriterBuilder<String>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(jobProperties.getOrderIntegrationOutputFailFile()))
                .name(ORDER_INTEGRATION_JSON_ITEM_WRITER)
                .build();

    }


}