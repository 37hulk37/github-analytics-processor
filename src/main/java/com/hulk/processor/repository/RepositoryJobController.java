package com.hulk.processor.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/repositories/jobs")
@RequiredArgsConstructor
public class RepositoryJobController {

    private final RepositoryJobManager repositoryJobManager;

    @PostMapping
    public JobExecution startJob(@RequestBody JobRequest request) {
        return repositoryJobManager.startJob(request.jobName(), request.startTime());
    }

    @GetMapping("/{jobName}")
    public Set<JobExecution> getJobs(@PathVariable String jobName) {
        return repositoryJobManager.getJobs(jobName);
    }

    @GetMapping("/names")
    public List<String> getJobNames() {
        return repositoryJobManager.getJobNames();
    }

    public record JobRequest(
        String jobName,
        LocalTime startTime
    ) {
    }

}
