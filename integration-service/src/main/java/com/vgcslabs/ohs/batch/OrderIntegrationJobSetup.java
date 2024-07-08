package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.dto.OrderIntegrationDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import static com.vgcslabs.ohs.batch.BatchJobConstants.ORDER_INTEGRATION_JOB;
import static com.vgcslabs.ohs.batch.BatchJobConstants.ORDER_INTEGRATION_STEP;

@Component
public class OrderIntegrationJobSetup {
    private final ItemProcessor<OrderIntegrationDto, String> processor;
    private final ItemReader<OrderIntegrationDto> reader;
    private final OrderIntegrationClassifierWriter classifier;
    private final ItemStreamWriter<String> successWriter;
    private final ItemStreamWriter<String> failWriter;

    public OrderIntegrationJobSetup(
            @Qualifier("orderIntegrationFlatFileItemReader") ItemReader<OrderIntegrationDto> reader,
            ItemProcessor<OrderIntegrationDto, String> processor,
            @Qualifier("orderIntegrationJsonItemWriterSuccess") ItemStreamWriter<String> successWriter,
            @Qualifier("orderIntegrationJsonItemWriterFail") ItemStreamWriter<String> failWriter,
            OrderIntegrationClassifierWriter classifier
    ) {

        this.processor = processor;
        this.reader = reader;
        this.classifier = classifier;
        this.successWriter = successWriter;
        this.failWriter = failWriter;

    }

    @Bean("orderIntegrationStep")
    public Step importVisitorsStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager) {
        return new StepBuilder(ORDER_INTEGRATION_STEP, jobRepository)
                .<OrderIntegrationDto, String>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(classifierCompositeItemWriter())
                .stream(successWriter)
                .stream(failWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(Integer.MAX_VALUE)
                .build();
    }


    public ClassifierCompositeItemWriter<String> classifierCompositeItemWriter() {
        ClassifierCompositeItemWriter<String> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<>();
        classifierCompositeItemWriter.setClassifier(classifier);
        return classifierCompositeItemWriter;
    }

    @Bean("orderIntegrationJob")
    public Job orderIntegrationJob(JobRepository jobRepository, @Qualifier("orderIntegrationStep") Step step) {
        return new JobBuilder(ORDER_INTEGRATION_JOB, jobRepository)
                .start(step)
                .build();
    }
}
