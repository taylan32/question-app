package com.project.questionapp.business.responses;

import com.project.questionapp.entities.Comment;

import lombok.Data;

@Data
public class CommentResponse {

	private int id;
	private int postId;
	private int userId;
	private String text;
	private String userName;
	
	public CommentResponse(Comment comment) {
		this.id = comment.getId();
		this.postId = comment.getPost().getId();
		this.userId = comment.getUser().getId();
		this.text = comment.getText();
		this.userName = comment.getUser().getUserName();
	}
	
	
	
	
}
