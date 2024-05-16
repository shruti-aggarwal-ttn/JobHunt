package com.shruti.jobs.JobHunt.service.impl;

import com.shruti.jobs.JobHunt.db.service.JobService;
import com.shruti.jobs.JobHunt.dto.JobDto;
import com.shruti.jobs.JobHunt.exception.JobPostNotFoundException;
import com.shruti.jobs.JobHunt.service.UpdateJobService;
import com.shruti.jobs.JobHunt.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateJobServiceImpl implements UpdateJobService {

    final JobService jobService;
    @Override
    public void updateJobById(String id, JobDto jobDto) {
        JobDto jobsById = jobService.getJobsById(id);
        if(jobsById != null){
            ObjectMapperUtil.mapNonNullFields(jobDto, jobsById);
            jobService.saveJob(jobsById);
        }else {
            throw new JobPostNotFoundException("Record not found", HttpStatus.NOT_FOUND);
        }
    }
}