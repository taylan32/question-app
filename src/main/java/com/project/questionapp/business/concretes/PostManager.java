package com.project.questionapp.business.concretes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.project.questionapp.business.abstracts.PostService;
import com.project.questionapp.business.abstracts.UserService;
import com.project.questionapp.business.constants.Messages;
import com.project.questionapp.business.requests.post.CreatePostRequest;
import com.project.questionapp.business.requests.post.UpdatePostRequest;
import com.project.questionapp.business.responses.PostResponse;
import com.project.questionapp.core.exceptions.AlreadyExistsException;
import com.project.questionapp.core.exceptions.NotFoundException;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.core.utilities.results.SuccessDataResult;
import com.project.questionapp.core.utilities.results.SuccessResult;

import com.project.questionapp.dataAccess.PostDao;
import com.project.questionapp.entities.Post;
import com.project.questionapp.entities.User;

@Service
public class PostManager implements PostService {

	private PostDao postDao;
	private UserService userService;

	@Autowired
	public PostManager(PostDao postDao, UserService userService) {
		this.postDao = postDao;
		this.userService = userService;

	}

	@Override
	@Transactional
	public Result add(CreatePostRequest postRequest) throws Exception {
		if (this.postDao.getByTitle(postRequest.getTitle()) != null) {
			throw new AlreadyExistsException(Messages.postAlreadyExists);
		}
		User user = this.userService.getById(postRequest.getUserId()).getData();

		Post postToSave = new Post();
		postToSave.setText(postRequest.getText());
		postToSave.setTitle(postRequest.getTitle());
		postToSave.setUser(user);
		postToSave.setCreatedAt(new Date());
		this.postDao.save(postToSave);
		return new SuccessResult(Messages.postAdded);

	}

	@Override
	public Result delete(int id) throws Exception {
		if (!checkIfPostExists(id)) {
			throw new NotFoundException(Messages.postNotFound);
		}
		Post postToDelete = this.getById(id).getData();
		this.postDao.delete(postToDelete);
		return new SuccessResult(Messages.postDeleted);

	}

	@Override
	public Result update(int id, UpdatePostRequest updatetRequest) throws Exception {
		if (!checkIfPostExists(id)) {
			throw new NotFoundException(Messages.postNotFound);
		}

		Post postToUpdate = this.postDao.getById(id);
		postToUpdate.setText(updatetRequest.getText());
		postToUpdate.setTitle(updatetRequest.getTitle());
		this.postDao.save(postToUpdate);
		return new SuccessResult(Messages.postUpdated);
	}

	@Override
	public DataResult<Post> getById(int id) throws Exception {
		if (!checkIfPostExists(id)) {
			throw new NotFoundException(Messages.postNotFound);
		}
		
		return new SuccessDataResult<Post>(this.postDao.getById(id),Messages.postListed);
	}

	@Override
	public DataResult<PostResponse> getOnePostById(int postId) throws Exception {
		if (!checkIfPostExists(postId)) {
			throw new NotFoundException(Messages.postNotFound);
		}
		Post post = this.postDao.getById(postId);
		return new SuccessDataResult<PostResponse>(new PostResponse(post), Messages.postListed);
	}

	@Override
	public DataResult<List<PostResponse>> getAll() {
		List<Post> posts = this.postDao.findAll();
		return new SuccessDataResult<List<PostResponse>>(
				posts.stream().map(post -> new PostResponse(post)).collect(Collectors.toList()), Messages.postListed);
	}

	
	@Override
	public DataResult<List<PostResponse>> getPostsByUserId(int userId) throws Exception {

		if (this.userService.getById(userId).getData() == null) {
			throw new NotFoundException(Messages.userNotFound);
		}
		List<Post> posts = this.postDao.getPostsByUserId(userId);
		return new SuccessDataResult<List<PostResponse>>(
				posts.stream().map(post -> new PostResponse(post)).collect(Collectors.toList()), Messages.postListed);

	}
	

	private boolean checkIfPostExists(int id) {
		if (this.postDao.getById(id) == null) {
			return false;
		}
		return true;
	}

}
