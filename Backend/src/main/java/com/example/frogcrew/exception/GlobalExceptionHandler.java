package com.example.frogcrew.exception;

import com.example.frogcrew.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseBody
    public Result handleCrewMemberNotFound(ObjectNotFoundException ex) {
        return new Result(false, HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleValidationException(MethodArgumentNotValidException ex) {
        // Extracting validation errors
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> errorMap = new HashMap<>(errors.size());

        // Loop through each error and map field name to error message
        errors.forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(field, message);
        });

        return new Result(false, HttpStatus.BAD_REQUEST.value(), "Provided arguments are invalid, see data for details.", errorMap);
    }
    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseBody
    public Result handleDuplicateEmailException(DuplicateEmailException ex) {
        return new Result(false, HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
