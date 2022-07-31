package com.project.questionapp.business.responses;

import com.project.questionapp.entities.Like;

import lombok.Data;

@Data
public class LikeResponse {

	private int id;
	private int userId;
	private int postId;
	
	public LikeResponse(Like like) {
		this.id = like.getId();
		this.userId = like.getUser().getId();
		this.postId = like.getPost().getId();
	}
	
}
