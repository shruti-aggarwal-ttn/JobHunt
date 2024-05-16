package com.shruti.jobs.JobHunt.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class JobPostNotFoundException extends RuntimeException{

    String message;
    HttpStatus httpStatus;

    public JobPostNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}