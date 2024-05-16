package com.shruti.jobs.JobHunt.service.impl;

import com.shruti.jobs.JobHunt.dto.JobDto;
import com.shruti.jobs.JobHunt.db.service.JobService;
import com.shruti.jobs.JobHunt.service.SaveJobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveJobsServiceImpl implements SaveJobsService {

    final JobService jobService;

    @Override
    public void saveJob(JobDto jobDto) {
        jobService.saveJob(jobDto);
    }
}