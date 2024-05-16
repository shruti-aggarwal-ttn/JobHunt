package com.shruti.jobs.JobHunt.db.service.impl;

import com.shruti.jobs.JobHunt.db.entity.JobEntity;
import com.shruti.jobs.JobHunt.db.service.JobService;
import com.shruti.jobs.JobHunt.dto.JobDto;
import com.shruti.jobs.JobHunt.db.repository.JobsRepository;
import com.shruti.jobs.JobHunt.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    final JobsRepository jobsRepository;

    @Override
    public List<JobDto> getAllJobs(){
       return ObjectMapperUtil.mapAll(jobsRepository.findAll(), JobDto.class);
    }

    @Override
    public void saveJob(JobDto jobDto) {
        jobsRepository.save(ObjectMapperUtil.map(jobDto, JobEntity.class));
    }

    @Override
    public JobDto getJobsById(String id) {
        Optional<JobEntity> jobEntityNew = Optional.of(jobsRepository.findById(id)).orElse(null);
        return jobEntityNew.map(entityNew -> ObjectMapperUtil.map(entityNew, JobDto.class)).orElse(null);
    }
}