package com.example.tw_thainguyen.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
    
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s đã tồn tại với %s: '%s'", resourceName, fieldName, fieldValue));
    }
}

