package com.htp.controller;

import com.htp.controller.exceptions.ExceptionMessage;
import com.htp.controller.exceptions.NotEnoughShirtsException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends DefaultHandlerExceptionResolver {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotEnoughShirtsException.class)
	public ResponseEntity<ExceptionMessage> handleNotEnoughShirtsException(NotEnoughShirtsException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({
			MalformedJwtException.class,
			UnsupportedJwtException.class,
			SignatureException.class,
			ExpiredJwtException.class
	})
	public ResponseEntity<ExceptionMessage> handleJwtException(MalformedJwtException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(
				new ExceptionMessage("Invalid or expired token"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionMessage> handleIllegalArgumentException(
			IllegalArgumentException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ExceptionMessage> handleUsernameNotFoundException(
			UsernameNotFoundException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(
				new ExceptionMessage("User with such login not found"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionMessage> handleBadCredentialsException(BadCredentialsException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionMessage> handleAuthenticationException(AuthenticationException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ExceptionMessage> handleIOException(IOException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(
				new ExceptionMessage(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ExceptionMessage> handleNumberFormatException(NumberFormatException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(
				new ExceptionMessage(
						"Bad request. The request could not be understood by the server. You should not repeat the request without modifications"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionMessage> handleOthersException(Exception e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(
				new ExceptionMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
