package com.shruti.jobs.JobHunt.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class JobDto {

    ObjectId id;
    String role;
    String description;
    Integer minExp;
    Integer maxExp;
    List<String> skills;
    String location;
    String education;
    String industryType;
    String department;
    String employmentType;
    List<String> perks;
    String company;
}