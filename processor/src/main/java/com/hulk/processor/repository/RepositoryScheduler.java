package com.hulk.processor.repository;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class RepositoryScheduler {

    private final RepositoryJobManager repositoryJobManager;

    @Async("repositorySchedulerExecutor")
    @Scheduled(cron = "0 0/30 * * * *")
    public void startRepositoriesProcessing() {
        repositoryJobManager.startJob("repositoryMetricsJob", LocalTime.now());
    }

    @Async("repositoryMlSchedulerExecutor")
    @Scheduled(cron = "0 0 * * * *")
    public void startMlProcessing() {
        repositoryJobManager.startJob("repositoryMlJob", LocalTime.now());
    }

}
