package com.shruti.jobs.JobHunt.exceptionhandler;

import com.shruti.jobs.JobHunt.error.ErrorDto;
import com.shruti.jobs.JobHunt.exception.JobPostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(JobPostNotFoundException.class)
    public ResponseEntity<ErrorDto> multipleAgentRecordsExistExceptionExceptionHandler(final JobPostNotFoundException e) {
        ErrorDto errorDTO = new ErrorDto();
        errorDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDTO,e.getHttpStatus());
    }
}