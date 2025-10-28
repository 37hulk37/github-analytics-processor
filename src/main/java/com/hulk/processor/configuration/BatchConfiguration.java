package com.hulk.processor.configuration;

import com.hulk.processor.repository.Repository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Configuration
@EnableConfigurationProperties({TopicProperties.class})
public class BatchConfiguration {

    @Bean
    public ItemReader<Repository> repositoryKafkaReader(
        Properties repositoryCunsumerGroupProperties,
        TopicProperties topicProperties
    ) {
        return new KafkaItemReaderBuilder<Long, Repository>()
            .consumerProperties(repositoryCunsumerGroupProperties)
            .topic(topicProperties.topic())
            .build();
    }

    @Bean
    public CompositeItemWriter<Repository> compositeRepositoryWriter(List<ItemWriter<? super Repository>> itemWriters) {
        var itemWriter = new CompositeItemWriter<Repository>();
        itemWriter.setDelegates(itemWriters);

        return itemWriter;
    }

    @Bean
    public ItemReader<Repository> repositoryMlReader(
        Properties mlConsumerGroupProperties,
        TopicProperties topicProperties
    ) {
        return new KafkaItemReaderBuilder<Long, Repository>()
            .consumerProperties(mlConsumerGroupProperties)
            .topic(topicProperties.topic())
            .build();
    }

    @Bean
    public CompositeItemWriter<CompletableFuture<Repository>> compositeMlWriter(List<ItemWriter<? super CompletableFuture<Repository>>> itemWriters) {
        var itemWriter = new CompositeItemWriter<CompletableFuture<Repository>>();
        itemWriter.setDelegates(itemWriters);

        return itemWriter;
    }

    @Bean
    public Job calculateMetricsJob(
        Step repositoryStep,
        Step mlStep,
        JobRepository jobRepository
    ) {
        return new JobBuilder("calculateMetricsJob", jobRepository)
            .start(repositoryStep)
            .next(mlStep)
            .build();
    }

    @Bean
    public Step repositoryStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Repository> repositoryKafkaReader,
        CompositeItemWriter<Repository> compositeRepositoryWriter
    ) {
        return new StepBuilder("repositoryStep", jobRepository)
            .<Repository, Repository>chunk(100, transactionManager)
            .reader(repositoryKafkaReader)
            .writer(compositeRepositoryWriter)
            .readerIsTransactionalQueue()
            .faultTolerant()
            .build();
    }

    @Bean
    public Step mlStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Repository> repositoryMlReader,
        CompositeItemWriter<Repository> compositeMlWriter
    ) {
        return new StepBuilder("mlStep", jobRepository)
            .<Repository, Repository>chunk(25, transactionManager)
            .reader(repositoryMlReader)
            .writer(compositeMlWriter)
            .readerIsTransactionalQueue()
            .faultTolerant()
            .build();
    }

    @Bean
    public TaskExecutor repositorySchedulerExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setAwaitTerminationSeconds(10);

        return executor;
    }

}
