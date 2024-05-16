package com.shruti.jobs.JobHunt.service;

import com.shruti.jobs.JobHunt.dto.JobDto;
import com.shruti.jobs.JobHunt.service.input.SearchInput;

import java.util.List;

public interface SearchJobsService {

    List<JobDto> searchByText(String text , SearchInput input);
}