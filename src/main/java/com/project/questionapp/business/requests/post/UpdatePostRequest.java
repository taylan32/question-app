package com.project.questionapp.business.requests.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;
@Data
public class UpdatePostRequest {

	@NotBlank(message = "Title is not allowed to be empty")
	@NotNull
	@Size(min = 5, max = 50, message = "Title must be between 5 and 50")
	private String title;

	@NotBlank(message = "Text is not allowed to be empty")
	@NotNull
	@Size(min = 10, message = "Text must have at least 10 characters")
	private String text;
}
