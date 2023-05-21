package com.david.movestore.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = NotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
    WebRequest request, EntityNotFoundException ex
  ) {
    System.out.println("Here");
    return handleExceptionInternal(
      ex,
      ex.getMessage(),
      new HttpHeaders(),
      HttpStatus.BAD_REQUEST,
      request
    );
  }
}
