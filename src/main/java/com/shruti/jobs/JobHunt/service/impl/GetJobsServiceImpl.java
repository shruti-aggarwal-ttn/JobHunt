package com.shruti.jobs.JobHunt.service.impl;

import com.shruti.jobs.JobHunt.dto.JobDto;
import com.shruti.jobs.JobHunt.db.service.JobService;
import com.shruti.jobs.JobHunt.service.GetJobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetJobsServiceImpl implements GetJobsService {

    final JobService jobService;

    @Override
    public List<JobDto> getJobs() {
        return jobService.getAllJobs();
    }

    @Override
    public JobDto getJobsById(String id) {
        return jobService.getJobsById(id);
    }
}