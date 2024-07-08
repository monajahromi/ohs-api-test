package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.dto.OrderIntegrationData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.vgcslabs.ohs.batch.BatchJobConstants.ORDER_INTEGRATION_FLAT_FILE_ITEM_READER;
import static com.vgcslabs.ohs.util.BatchMappingUtils.getFieldNames;


@Component
@RequiredArgsConstructor
public class OrderIntegrationDataReader {
    private final BatchJobProperties jobProperties;

    @Bean("orderIntegrationFlatFileItemReader")
    @StepScope
    public FlatFileItemReader<OrderIntegrationData> reader() throws IOException {
        return new FlatFileItemReaderBuilder<OrderIntegrationData>()

                .name(ORDER_INTEGRATION_FLAT_FILE_ITEM_READER)
                .resource(new FileSystemResource(jobProperties.getOrderIntegrationInputFile()))
                .linesToSkip(1)
                .lineMapper(orderIntegrationDataLineMapper())
                .build();
    }
    public LineMapper<OrderIntegrationData> orderIntegrationDataLineMapper() {
        DefaultLineMapper<OrderIntegrationData> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(orderIntegrationDataLineTokenizer());
        lineMapper.setFieldSetMapper(orderIntegrationDataFieldSetMapper());
        return lineMapper;
    }


    public LineTokenizer orderIntegrationDataLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(getFieldNames((OrderIntegrationData.class)));
        return tokenizer;
    }

    public FieldSetMapper<OrderIntegrationData> orderIntegrationDataFieldSetMapper() {
        BeanWrapperFieldSetMapper<OrderIntegrationData> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(OrderIntegrationData.class);
        return fieldSetMapper;
    }


}