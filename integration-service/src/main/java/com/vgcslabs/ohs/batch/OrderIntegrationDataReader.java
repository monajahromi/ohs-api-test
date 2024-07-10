package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.config.BatchJobConfig;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import static com.vgcslabs.ohs.batch.BatchJobConstants.ORDER_INTEGRATION_FLAT_FILE_ITEM_READER;
import static com.vgcslabs.ohs.batch.BatchMappingUtils.getFieldNames;


@Component
@RequiredArgsConstructor
@Slf4j
public class OrderIntegrationDataReader {
    private final BatchJobConfig jobProperties;

    @Bean("orderIntegrationFlatFileItemReader")
    public FlatFileItemReader<OrderIntegrationDto> reader() {
        try {
            return new FlatFileItemReaderBuilder<OrderIntegrationDto>()
                    .name(ORDER_INTEGRATION_FLAT_FILE_ITEM_READER)
                    .resource(new FileSystemResource(jobProperties.getOrderIntegrationInputFile()))
                    .linesToSkip(jobProperties.getLinesToSkip())
                    .lineMapper(orderIntegrationDataLineMapper())
                    .build();
        } catch (Exception e) {
            log.error("Error creating FlatFileItemReader", e);
            throw new RuntimeException("Failed to create FlatFileItemReader", e);
        }
    }

    public LineMapper<OrderIntegrationDto> orderIntegrationDataLineMapper() {
        DefaultLineMapper<OrderIntegrationDto> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(orderIntegrationDataLineTokenizer());
        lineMapper.setFieldSetMapper(new OrderIntegrationFieldSetMapper());
        return lineMapper;
    }

    public LineTokenizer orderIntegrationDataLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(getFieldNames((OrderIntegrationDto.class)));
        return tokenizer;
    }

}