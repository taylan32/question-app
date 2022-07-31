package com.project.questionapp.business.requests.comment;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateCommentRequest {
	
	@NotNull
	private int userId;
	
	@NotNull
	private int postId;
	
	@NotNull
	@NotBlank(message = "Comment text is not allowed to be empty")
	@Size(min = 2, max = 100, message = "Comment text must be betweeen 2 and 100 characters")
	private String text;

}
