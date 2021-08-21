package com.example.ordermanagement.presentation.exceptionController;

import com.example.ordermanagement.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoSuchDataExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchDataException.class)
    public String handleNotFound(){
        return "{}";
    }
}
