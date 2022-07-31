package com.project.questionapp.business.abstracts;

import java.util.List;

import com.project.questionapp.business.requests.post.CreatePostRequest;
import com.project.questionapp.business.requests.post.UpdatePostRequest;
import com.project.questionapp.business.responses.PostResponse;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.entities.Post;

public interface PostService{

	Result add(CreatePostRequest entity) throws Exception;

	Result delete(int id) throws Exception;

	Result update(int id, UpdatePostRequest updateRequest) throws Exception;

	DataResult<Post> getById(int id) throws Exception;

	DataResult<List<PostResponse>> getAll();

	DataResult<List<PostResponse>> getPostsByUserId(int userId) throws Exception;
	
	DataResult<PostResponse> getOnePostById(int postId) throws Exception;
	
	
}
