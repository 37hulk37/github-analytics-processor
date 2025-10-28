package com.hulk.processor.repository;

import com.hulk.processor.exception.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class RepositoryScheduler {

    private final Job repositoryJob;
    private final JobLauncher jobLauncher;

    @Async("repositorySchedulerExecutor")
    @Scheduled(cron = "0 0/30 * * * *")
    public void startProcessing() {
        var parameters = new JobParametersBuilder()
                .addString("id", UUID.randomUUID().toString())
                .addLocalDateTime("createdAt", LocalDateTime.now())
                .toJobParameters();

        try {
            jobLauncher.run(repositoryJob, parameters);
        } catch (JobExecutionException e) {
            throw new ApplicationException("Can not run job with parameters:" + parameters, e);
        }
    }
}
