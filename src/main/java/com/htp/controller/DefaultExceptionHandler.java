package com.htp.controller;

import com.htp.controller.exceptions.ExceptionMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException e) {
    // logger?
    return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    UnsupportedJwtException.class,
    MalformedJwtException.class,
    SignatureException.class,
    ExpiredJwtException.class
  })
  public ResponseEntity<ExceptionMessage> handleJwtException(JwtException e) {
    return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionMessage> handleIllegalArgumentException(
      IllegalArgumentException e) {
    return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ExceptionMessage> handleAuthenticationException(AuthenticationException e) {
    return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ExceptionMessage> handleIOException(IOException e) {
    return new ResponseEntity<>(
        new ExceptionMessage(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionMessage> handleOthersException(Exception e) {
    return new ResponseEntity<>(
        new ExceptionMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // new Exception IO for amazon s3 loading images
}
