package com.shruti.jobs.JobHunt.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@Data
@Document(collection = "job_post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobEntity {

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