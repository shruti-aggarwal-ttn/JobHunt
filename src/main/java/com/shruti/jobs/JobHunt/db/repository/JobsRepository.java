package com.shruti.jobs.JobHunt.db.repository;

import com.shruti.jobs.JobHunt.db.entity.JobEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends MongoRepository<JobEntity,String> {
}