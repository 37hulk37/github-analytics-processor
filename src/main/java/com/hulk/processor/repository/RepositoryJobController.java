package com.hulk.processor.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/repositories/jobs")
@RequiredArgsConstructor
public class RepositoryJobController {

    private final RepositoryJobManager repositoryJobManager;

    @PostMapping
    public Mono<JobExecution> startJob(@RequestBody JobRequest request) {
        return repositoryJobManager.startJob(request.jobName(), request.startTime());
    }

    @GetMapping("/{jobName}")
    public Flux<JobExecution> getJobs(@PathVariable String jobName) {
        return repositoryJobManager.getJobs(jobName);
    }

    public record JobRequest(
        String jobName,
        LocalTime startTime
    ) {
    }

}
