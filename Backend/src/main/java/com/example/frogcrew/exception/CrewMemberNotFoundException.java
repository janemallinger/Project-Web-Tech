package com.example.frogcrew.exception;

public class CrewMemberNotFoundException extends RuntimeException {
    public CrewMemberNotFoundException(Long id) {
        super("Could not find crew member with id " + id);
    }
}
