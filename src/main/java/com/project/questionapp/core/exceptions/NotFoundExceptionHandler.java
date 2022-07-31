package com.project.questionapp.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.questionapp.core.utilities.results.ErrorDataResult;

@ControllerAdvice
public class NotFoundExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorDataResult<Object> handleNotFoundException(NotFoundException exception) {
		return new ErrorDataResult<Object>(exception.getMessage(), "Error occured");
	}
	
}
