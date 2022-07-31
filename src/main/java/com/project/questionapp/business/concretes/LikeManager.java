package com.project.questionapp.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.questionapp.business.abstracts.LikeService;
import com.project.questionapp.business.abstracts.UserService;
import com.project.questionapp.business.constants.Messages;
import com.project.questionapp.business.requests.post.CreateLikeRequest;
import com.project.questionapp.business.responses.LikeResponse;
import com.project.questionapp.core.exceptions.NotFoundException;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.core.utilities.results.SuccessResult;
import com.project.questionapp.dataAccess.LikeDao;
import com.project.questionapp.dataAccess.PostDao;
import com.project.questionapp.entities.Like;

@Service
public class LikeManager implements LikeService {

	private LikeDao likeDao;
	private PostDao postDao;
	private UserService userService;

	@Autowired
	public LikeManager(LikeDao likeDao, PostDao postDao, UserService userService) {
		this.likeDao = likeDao;
		this.postDao = postDao;
		this.userService = userService;
	}

	@Override
	public Result add(CreateLikeRequest likeRequest) throws Exception {
		Like like = new Like();
		if(this.postDao.getById(likeRequest.getPostId()) == null) {
			throw new NotFoundException(Messages.postNotFound);
		}
		like.setPost(this.postDao.getById(likeRequest.getPostId()));
		like.setUser(this.userService.getById(likeRequest.getUserId()).getData());
		this.likeDao.save(like);
		return new SuccessResult("Liked");
	}

	@Override
	public Result delete(int userId, int postId) throws Exception {
		Like likeToDelete = this.likeDao.getByUser_IdAndPost_Id(userId, postId);
		if(likeToDelete == null) {
			throw new NotFoundException("Unexpected error.");
		}
		this.likeDao.delete(likeToDelete);
		return new SuccessResult();
	}

	@Override
	public int countLikesByPostId(int postId) throws Exception {

		return this.likeDao.countByPost_Id(postId);
	}

	@Override
	public List<LikeResponse> getAllLikes() {
		List<Like> list = this.likeDao.findAll();
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

	@Override
	public boolean checkIfUserLikesPost(int userId, int postId) {
		if(this.likeDao.getByUser_IdAndPost_Id(userId, postId) == null) {
			return false;
		}
		return true;
	}
	

}
