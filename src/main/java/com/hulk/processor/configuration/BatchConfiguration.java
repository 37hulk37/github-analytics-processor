package com.hulk.processor.configuration;

import com.hulk.processor.repository.RepositoryInfo;
import com.hulk.processor.repository.RepositoryResult;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

@EnableConfigurationProperties({RepositoryConsumerProperties.class})
@Configuration
public class BatchConfiguration {

    @Bean
    public ItemReader<RepositoryInfo> repositoryReader(
        RepositoryConsumerProperties properties
    ) {
        return new KafkaItemReaderBuilder<UUID, RepositoryInfo>()
                .topic(properties.topicName())
                .consumerProperties()
                .build();
    }

    @Bean
    public Job repositoryJob(Step repositryStep, JobRepository jobRepository) {
        return new JobBuilder("repositoryJob", jobRepository)
                .start(repositryStep)
                .build();
    }

    @Bean
    public Step repositoryStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<RepositoryInfo> itemReader,
            ItemProcessor<RepositoryInfo, RepositoryResult> itemProcessor,
            ItemWriter<RepositoryResult> itemWriter
    ) {
        return new StepBuilder("repositoryStep", jobRepository)
                .<RepositoryInfo, RepositoryResult>chunk(100, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .readerIsTransactionalQueue()
                .faultTolerant()
                .noRetry()
                .build();
    }


    @Bean
    public TaskExecutor repositoryProcessingExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setAwaitTerminationSeconds(10);

        return executor;
    }

}
