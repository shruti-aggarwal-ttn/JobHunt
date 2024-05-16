package com.shruti.jobs.JobHunt.service.impl;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.shruti.jobs.JobHunt.dto.JobDto;
import com.shruti.jobs.JobHunt.service.SearchJobsService;
import com.shruti.jobs.JobHunt.service.input.SearchInput;
import com.shruti.jobs.JobHunt.util.JobFieldValidationUtils;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchJobsServiceImpl implements SearchJobsService {

    final MongoClient mongoClient;
    final MongoConverter mongoConverter;
    @Value("${spring.data.mongodb.database}")
    String dbName;
    @Value("${spring.data.mongodb.collection}")
    String dbCollection;
    @Override
    public List<JobDto> searchByText(String text , SearchInput input) {
        List<JobDto> jobList = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = database.getCollection(dbCollection);
        List<Document> stages = new ArrayList<>();
        stages.add(new Document("$search",
                new Document( "index", "JobSearch").append("text",
                        new Document("query", text).append("path", new Document(
                                "wildcard", "*")))));
        if(input != null){
            if(input.getSort() != null && input.getSortBy() != null && JobFieldValidationUtils.isValidField(input.getSortBy())){
                stages.add(new Document("$sort",
                        new Document(input.getSortBy(), Sort.Direction.ASC.equals(input.getSort()) ? 1 : -1)));
            }
            if(input.getPageNo()!= null && input.getPageSize() != null) {
                if(input.getPageNo() > 0) {
                    int skip = (input.getPageNo() - 1) * input.getPageSize();
                    stages.add(new Document("$skip", skip));
                }
                stages.add(new Document("$limit", input.getPageSize()));
            }
        }
        AggregateIterable<Document> result = collection.aggregate(stages);
        result.forEach(document -> jobList.add( mongoConverter.read(JobDto.class,document)));
        return jobList;
    }
}