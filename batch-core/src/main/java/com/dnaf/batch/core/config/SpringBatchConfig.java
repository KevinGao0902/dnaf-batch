package com.dnaf.batch.core.config;

import com.dnaf.batch.core.demo.batch.LateChargeItemReader;
import com.dnaf.batch.core.demo.batch.LateChargeItemWriter;
import com.dnaf.batch.core.demo.batch.LateChargePartitioner;
import com.dnaf.batch.core.demo.domain.LateChargeItem;
import com.dnaf.batch.core.processor.LateChargeItemProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    private final LateChargePartitioner partitioner;
    private final LateChargeItemReader reader;
    private final LateChargeItemProcessor processor;
    private final LateChargeItemWriter writer;

    public SpringBatchConfig(LateChargePartitioner partitioner, LateChargeItemReader reader, LateChargeItemProcessor processor, LateChargeItemWriter writer) {
        this.partitioner = partitioner;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    public Step workerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("workerStep", jobRepository)
                .<LateChargeItem, LateChargeItem>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                    .retryLimit(3)
                    .retry(Exception.class)
                    .skipLimit(10)
                    .skip(Exception.class)
                .build();
    }

    @Bean
    public Step masterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("masterStep", jobRepository)
                .partitioner("workerStep", partitioner)
                .step(workerStep(jobRepository, transactionManager))
                .gridSize(4)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Job lateChargeJob(JobRepository jobRepository, @Qualifier("masterStep") Step masterStep) {
        return new JobBuilder("lateChargeJob", jobRepository)
                .start(masterStep)
                .build();
    }
}