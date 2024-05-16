package com.shruti.jobs.JobHunt.service;

import com.shruti.jobs.JobHunt.dto.JobDto;

import java.util.List;

public interface GetJobsService {
    List<JobDto> getJobs();

   JobDto getJobsById(String id);
}