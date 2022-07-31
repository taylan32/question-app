package com.project.questionapp.business.abstracts;

import java.util.List;

import com.project.questionapp.business.responses.UserResponse;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.entities.User;

public interface UserService {
	Result add(User entity) throws Exception;

	Result delete(int id) throws Exception;

	Result update(User entity) throws Exception;

	DataResult<User> getById(int id) throws Exception;

	DataResult<List<UserResponse>> getAll();
	
	DataResult<User> getByUserName(String userName);

	DataResult<List<Object>> getUserActivity(int userId);
	
	DataResult<UserResponse> getOneUserById(int userId) throws Exception;

	Result changeAvatar(int userId, int avatar) throws Exception;

}
