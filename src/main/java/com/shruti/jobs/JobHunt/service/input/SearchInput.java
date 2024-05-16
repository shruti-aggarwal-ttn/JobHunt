package com.shruti.jobs.JobHunt.service.input;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SearchInput {

    String sortBy ;
    Sort.Direction sort = Sort.Direction.ASC;
    Integer pageNo;
    Integer pageSize;
}