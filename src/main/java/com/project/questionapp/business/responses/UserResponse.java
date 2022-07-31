package com.project.questionapp.business.responses;

import com.project.questionapp.entities.User;

import lombok.Data;

@Data
public class UserResponse {

	private int id;
	private String userName;
	private int avatar;

	public UserResponse(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.avatar = user.getAvatar();
	}
}
