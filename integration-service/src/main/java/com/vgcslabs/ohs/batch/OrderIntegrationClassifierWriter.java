package com.vgcslabs.ohs.batch;


import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class OrderIntegrationClassifierWriter implements Classifier<String, ItemWriter<? super String>> {

    private final ItemStreamWriter<String> successWriter;
    private final ItemStreamWriter<String> failWriter;

    public OrderIntegrationClassifierWriter(
            @Qualifier("orderIntegrationJsonItemWriterSuccess") ItemStreamWriter<String> successWriter,
            @Qualifier("orderIntegrationJsonItemWriterFail") ItemStreamWriter<String> failWriter) {
        this.successWriter = successWriter;
        this.failWriter = failWriter;

    }

    @Override
    public ItemWriter<String> classify(String item) {
        if (item.equals("a"))
            return successWriter;
        else
            return failWriter;
    }

}