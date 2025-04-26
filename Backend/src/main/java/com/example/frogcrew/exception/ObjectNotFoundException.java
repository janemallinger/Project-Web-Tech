package com.example.frogcrew.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(Long id) {
        super("Could not find user with id " + id);
    }
}
