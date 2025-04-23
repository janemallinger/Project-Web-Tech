package com.example.frogcrew.exception;

import com.example.frogcrew.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CrewMemberNotFoundException.class)
    public Result handleCrewMemberNotFound(CrewMemberNotFoundException except ){
        return new Result(false, HttpStatus.NOT_FOUND.value(), except.getMessage());
    }

}
