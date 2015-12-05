package com.drmtx.app.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.drmtx.app.api.CommentAnalysisController;



@ControllerAdvice
public class CommentAnalysisExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger LOGGER = Logger.getLogger(CommentAnalysisExceptionHandler.class.getName());
	
	@ExceptionHandler({ CommentAnalysisException.class })
    protected ResponseEntity<String> handleInvalidRequest(Exception e) {
		CommentAnalysisException cae = (CommentAnalysisException) e;
		LOGGER.log(Level.SEVERE, cae.getMessage(), cae);
		return new ResponseEntity<String>(cae.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String handleException(Exception e) {
		LOGGER.log(Level.SEVERE, e.getMessage(), e);
		return "Invalid or Bad Request. Kindly check the request";
	}
}
