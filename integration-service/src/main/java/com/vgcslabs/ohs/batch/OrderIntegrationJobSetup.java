package com.vgcslabs.ohs.batch;

import com.vgcslabs.ohs.dto.OrderBatchJobResponseDto;
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
    private final ItemProcessor<OrderIntegrationDto, OrderBatchJobResponseDto> processor;
    private final ItemReader<OrderIntegrationDto> reader;
    private final OrderIntegrationClassifierWriter classifier;
    private final ItemStreamWriter<OrderBatchJobResponseDto> successWriter;
    private final ItemStreamWriter<OrderBatchJobResponseDto> failWriter;

    public OrderIntegrationJobSetup(
            @Qualifier("orderIntegrationFlatFileItemReader") ItemReader<OrderIntegrationDto> reader,
            ItemProcessor<OrderIntegrationDto, OrderBatchJobResponseDto> processor,
            @Qualifier("orderIntegrationJsonItemWriterSuccess") ItemStreamWriter<OrderBatchJobResponseDto> successWriter,
            @Qualifier("orderIntegrationJsonItemWriterFail") ItemStreamWriter<OrderBatchJobResponseDto> failWriter,
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
                .<OrderIntegrationDto, OrderBatchJobResponseDto>chunk(2, transactionManager)
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


    public ClassifierCompositeItemWriter<OrderBatchJobResponseDto> classifierCompositeItemWriter() {
        ClassifierCompositeItemWriter<OrderBatchJobResponseDto> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<>();
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
