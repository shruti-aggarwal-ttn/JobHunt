package com.shruti.jobs.JobHunt.db.service;

import com.shruti.jobs.JobHunt.dto.JobDto;

import java.util.List;

public interface JobService {
    List<JobDto> getAllJobs();

    void saveJob(JobDto jobDto);

    JobDto getJobsById(String id);
}