package com.example.frogcrew.exception;

import com.example.frogcrew.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseBody
    public Result handleCrewMemberNotFound(ObjectNotFoundException ex ){
        return new Result(false, HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
