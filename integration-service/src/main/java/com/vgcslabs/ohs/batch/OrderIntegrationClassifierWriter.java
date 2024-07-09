package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class OrderIntegrationClassifierWriter implements Classifier<OrderBatchJobResponseDto, ItemWriter<? super OrderBatchJobResponseDto>> {

    private final ItemStreamWriter<OrderBatchJobResponseDto> successWriter;
    private final ItemStreamWriter<OrderBatchJobResponseDto> failWriter;

    public OrderIntegrationClassifierWriter(
            @Qualifier("orderIntegrationJsonItemWriterSuccess") ItemStreamWriter<OrderBatchJobResponseDto> successWriter,
            @Qualifier("orderIntegrationJsonItemWriterFail") ItemStreamWriter<OrderBatchJobResponseDto> failWriter) {
        this.successWriter = successWriter;
        this.failWriter = failWriter;

    }

    @Override
    public ItemWriter<OrderBatchJobResponseDto> classify(OrderBatchJobResponseDto item) {
            return successWriter;
    }

}