package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.config.BatchJobProperties;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.vgcslabs.ohs.batch.BatchJobConstants.ORDER_INTEGRATION_FLAT_FILE_ITEM_READER;
import static com.vgcslabs.ohs.util.BatchMappingUtils.getFieldNames;


@Component
@RequiredArgsConstructor
public class OrderIntegrationDataReader {
    private final BatchJobProperties jobProperties;

    @Bean("orderIntegrationFlatFileItemReader")
    public FlatFileItemReader<OrderIntegrationDto> reader() {
        return new FlatFileItemReaderBuilder<OrderIntegrationDto>()

                .name(ORDER_INTEGRATION_FLAT_FILE_ITEM_READER)
                .resource(new FileSystemResource(jobProperties.getOrderIntegrationInputFile()))
                .linesToSkip(1)
                .lineMapper(orderIntegrationDataLineMapper())
                .build();
    }

    public LineMapper<OrderIntegrationDto> orderIntegrationDataLineMapper() {
        DefaultLineMapper<OrderIntegrationDto> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(orderIntegrationDataLineTokenizer());
        lineMapper.setFieldSetMapper(orderIntegrationDataFieldSetMapper());
        return lineMapper;
    }

    public LineTokenizer orderIntegrationDataLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(getFieldNames((OrderIntegrationDto.class)));
        return tokenizer;
    }

    public FieldSetMapper<OrderIntegrationDto> orderIntegrationDataFieldSetMapper() {
        BeanWrapperFieldSetMapper<OrderIntegrationDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(OrderIntegrationDto.class);
        return fieldSetMapper;
    }


}