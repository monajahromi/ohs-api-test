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
    private final ItemStreamWriter<OrderBatchJobResponseDto> writer;

    public OrderIntegrationJobSetup(
            @Qualifier("orderIntegrationFlatFileItemReader") ItemReader<OrderIntegrationDto> reader,
            ItemProcessor<OrderIntegrationDto, OrderBatchJobResponseDto> processor,
            @Qualifier("orderIntegrationJsonItemWriter") ItemStreamWriter<OrderBatchJobResponseDto> writer
    ) {

        this.processor = processor;
        this.reader = reader;
        this.writer = writer;
   }

    @Bean("orderIntegrationStep")
    public Step importVisitorsStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager) {
        return new StepBuilder(ORDER_INTEGRATION_STEP, jobRepository)
                .<OrderIntegrationDto, OrderBatchJobResponseDto>chunk(20, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new OrderIntegrationSkipPolicy())
                .build();
    }

    @Bean("orderIntegrationJob")
    public Job orderIntegrationJob(JobRepository jobRepository, @Qualifier("orderIntegrationStep") Step step) {
        return new JobBuilder(ORDER_INTEGRATION_JOB, jobRepository)
                .start(step)
                .build();
    }
}
