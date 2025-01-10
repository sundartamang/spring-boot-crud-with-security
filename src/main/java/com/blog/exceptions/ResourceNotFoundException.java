package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    long longValue;
    String stringValue;


    public ResourceNotFoundException(String resourceName, String fieldName, long longValue) {
        super(String.format("%s not found with %s : %d", resourceName, fieldName, longValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.longValue = longValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String stringValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, stringValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.stringValue = stringValue;
    }
}
