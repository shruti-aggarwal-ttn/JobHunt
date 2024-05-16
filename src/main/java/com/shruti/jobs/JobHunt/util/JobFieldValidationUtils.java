package com.shruti.jobs.JobHunt.util;

import com.shruti.jobs.JobHunt.db.entity.JobEntity;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class JobFieldValidationUtils {
    private static final Set<String> VALID_FIELDS = new HashSet<>();

    static {
        // Use reflection to populate the VALID_FIELDS set
        Field[] fields = JobEntity.class.getDeclaredFields();
        for (Field field : fields) {
            VALID_FIELDS.add(field.getName());
        }
    }

    public static boolean isValidField(String fieldName) {
        return VALID_FIELDS.contains(fieldName);
    }
}