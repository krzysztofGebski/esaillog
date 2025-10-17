package com.esaillog.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * @param resourceName name of the resource e.g. Sailor
     * @param fieldName    name of the field e.g. id
     * @param identifier   the value of the identifier used for lookup e.g. 123
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object identifier) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, identifier));
    }
}
