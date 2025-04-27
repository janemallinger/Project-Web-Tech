package com.example.frogcrew.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName, Long id) {
        super("Could not find " + objectName + " with id " + id);
    }
}
