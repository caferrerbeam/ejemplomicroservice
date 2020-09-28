package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionManager {

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
  public String handleBusinessException(HttpServletRequest req, Exception exc){
    return exc.getMessage();
  }

}
