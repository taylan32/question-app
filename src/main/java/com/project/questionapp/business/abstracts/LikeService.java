package com.project.questionapp.business.abstracts;

import java.util.List;

import com.project.questionapp.business.requests.post.CreateLikeRequest;
import com.project.questionapp.business.responses.LikeResponse;
import com.project.questionapp.core.utilities.results.Result;

public interface LikeService {

	Result add(CreateLikeRequest likeRequest) throws Exception;
	
	Result delete(int userId, int postId) throws Exception;
	
	int countLikesByPostId(int postId) throws Exception;
	
	List<LikeResponse> getAllLikes();
	
	boolean checkIfUserLikesPost(int userId, int postId);
	
}
