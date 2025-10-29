package com.hulk.processor.repository;

import com.hulk.processor.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepositoryJobManager implements InitializingBean {

    private final JobExplorer jobExplorer;
    private final JobLauncher jobLauncher;
    private final List<Job> jobs;

    private Map<String, Job> jobsByName;

    @Override
    public void afterPropertiesSet() {
        jobsByName = jobs.stream()
            .collect(Collectors.toMap(Job::getName, job -> job));
    }

    public Mono<JobExecution> startJob(String jobName, LocalTime startTime) {
        var job = jobsByName.get(jobName);
        if (job == null) {
            throw new ApplicationException(String.format("Job '%s' not found", jobName));
        }

        var parameters = new JobParametersBuilder()
            .addString("id", UUID.randomUUID().toString())
            .addLocalTime("createdAt", startTime)
            .toJobParameters();

        try {
            var jobExecution = jobLauncher.run(job, parameters);

            return Mono.fromCallable(() -> jobExecution);
        } catch (JobExecutionException e) {
            return Mono.error(new ApplicationException("Can not run job with parameters:" + parameters, e));
        }
    }

    public Flux<JobExecution> getJobs(String jobName) {
        return Flux.fromIterable(jobExplorer.findRunningJobExecutions(jobName));
    }

}
