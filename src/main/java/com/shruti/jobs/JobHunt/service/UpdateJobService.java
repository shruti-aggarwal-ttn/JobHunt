package com.shruti.jobs.JobHunt.service;

import com.shruti.jobs.JobHunt.dto.JobDto;

public interface UpdateJobService {
    void updateJobById(String id, JobDto jobDto);
}