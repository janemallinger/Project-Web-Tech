package com.example.frogcrew.exception;

import com.example.frogcrew.system.Result;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.aspectj.apache.bcel.Repository.instanceOf;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleInvalidJson(HttpMessageNotReadableException ex) {
        // Check if the error is related to date parsing (e.g., LocalDate)
        if (ex.getRootCause() instanceof DateTimeParseException) {
            // Handle date format issues
            return new Result(false, HttpStatus.BAD_REQUEST.value(),
                    "Invalid date format provided. Please use the format YYYY-MM-DD for year or HH:mm:[ss] for time", null);
        }

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "Invalid JSON format");

        return new Result(false, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), errorMap);
    }
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Result handleConflict(ConflictException ex){
        return new Result(false, HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
