package com.shruti.jobs.JobHunt.controller;

import com.shruti.jobs.JobHunt.dto.JobDto;
import com.shruti.jobs.JobHunt.service.GetJobsService;
import com.shruti.jobs.JobHunt.service.SaveJobsService;
import com.shruti.jobs.JobHunt.service.SearchJobsService;
import com.shruti.jobs.JobHunt.service.UpdateJobService;
import com.shruti.jobs.JobHunt.service.input.SearchInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/new/jobs")
@RequiredArgsConstructor
public class JobController<T> {

    final SaveJobsService saveJobsService;
    final GetJobsService getJobsService;
    final SearchJobsService searchJobsService;
    final UpdateJobService updateJobService;
    @PostMapping
    public ResponseEntity<HttpStatus> saveJobs(@RequestBody JobDto jobs){
        saveJobsService.saveJob(jobs);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> getJobs(){
        List<JobDto> jobs = getJobsService.getJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDto>> search(@RequestParam(value = "text",required = false) String text , @RequestBody(required = false) SearchInput input){
        List<JobDto> jobs = searchJobsService.searchByText(text , input);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobsById(@PathVariable("id") String id){
        JobDto jobs = getJobsService.getJobsById(id);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJobsById(@PathVariable("id") String id , @RequestBody JobDto jobDto){
        updateJobService.updateJobById(id, jobDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}