package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {

  @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
  @ExceptionHandler(BusinessException.class)
  @ResponseBody
  public ErrorResponse businessError(HttpServletRequest req, Exception ex) {
    return new ErrorResponse(ex.getMessage(), "412");
  }
}