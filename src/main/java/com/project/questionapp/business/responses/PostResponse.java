package com.project.questionapp.business.responses;

import java.util.Date;

import com.project.questionapp.entities.Post;
import lombok.Data;

@Data
public class PostResponse {

	private int id;
	private int userId;
	private String userName;
	private String title;
	private String text;
	private Date createdAt;


	
	public PostResponse(Post post) {
		this.id = post.getId();
		this.userId = post.getUser().getId();
		this.userName = post.getUser().getUserName();
		this.title = post.getTitle();
		this.text = post.getText();
		this.createdAt = post.getCreatedAt();

	}
  
}
 