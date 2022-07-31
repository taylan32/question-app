package com.project.questionapp.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends Exception {

	public AlreadyExistsException(String message) {
		super(message);
	}
}
