package com.project.questionapp.business.requests.post;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class CreateLikeRequest {

	@NotNull
	private int userId;
	@NotNull
	private int postId;
}
