package com.project.questionapp.business.concretes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.questionapp.business.abstracts.CommentService;
import com.project.questionapp.business.abstracts.PostService;
import com.project.questionapp.business.abstracts.UserService;
import com.project.questionapp.business.constants.Messages;
import com.project.questionapp.business.requests.comment.CreateCommentRequest;
import com.project.questionapp.business.requests.comment.UpdateCommentRequest;
import com.project.questionapp.business.responses.CommentResponse;
import com.project.questionapp.core.exceptions.NotFoundException;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.core.utilities.results.SuccessDataResult;
import com.project.questionapp.core.utilities.results.SuccessResult;
import com.project.questionapp.dataAccess.CommentDao;
import com.project.questionapp.entities.Comment;

@Service
public class CommentManager implements CommentService {

	private CommentDao commentDao;
	private UserService userService;
	private PostService postService;

	@Autowired
	public CommentManager(CommentDao commentDao, UserService userService, PostService postService) {
		this.commentDao = commentDao;
		this.postService = postService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateCommentRequest createRequest) throws Exception {
		Comment commentToSave = new Comment();
		commentToSave.setText(createRequest.getText());
		commentToSave.setUser(this.userService.getById(createRequest.getUserId()).getData());
		commentToSave.setPost(this.postService.getById(createRequest.getPostId()).getData());
		commentToSave.setCreatedAt(new Date());
		this.commentDao.save(commentToSave);
		return new SuccessResult(Messages.commentAdded); 
	}

	@Override
	public Result update(int id, UpdateCommentRequest updateRequest) throws Exception {
		if (!checkIfCommentExists(id)) {
			throw new NotFoundException(Messages.commentNotFound);
		}
		Comment commentToUpdadate = this.commentDao.getById(id);
		commentToUpdadate.setText(updateRequest.getText());
		this.commentDao.save(commentToUpdadate);
		return new SuccessResult(Messages.commentUpdated);
	}

	@Override
	public Result delete(int id) throws Exception {
		if (!checkIfCommentExists(id)) {
			throw new NotFoundException(Messages.commentNotFound);
		}
		Comment commentToDelete = this.commentDao.getById(id);
		this.commentDao.delete(commentToDelete);
		return new SuccessResult(Messages.commentDeleted);
	}

	@Override
	public DataResult<List<CommentResponse>> getAllCommentsByPostId(int postId) throws Exception {
		if (this.postService.getById(postId).getData() == null) {
			throw new NotFoundException(Messages.postNotFound);
		}
		List<Comment> comments = this.commentDao.getByPost_Id(postId);
		return new SuccessDataResult<List<CommentResponse>>(
				comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList()),
				Messages.commentListed);
	}

	@Override
	public DataResult<List<Comment>> getAllCommentsByUserId(int userId) throws Exception {

		if (this.userService.getById(userId).getData() == null) {
			throw new NotFoundException(Messages.userNotFound);
		}
		return new SuccessDataResult<List<Comment>>(this.commentDao.getByUser_Id(userId), Messages.commentListed);
	}

	@Override
	public DataResult<Comment> getById(int id) throws Exception {
		if (!checkIfCommentExists(id)) {
			throw new NotFoundException(Messages.commentNotFound);
		}
		return new SuccessDataResult<Comment>(this.commentDao.getById(id), Messages.commentListed);
	}
	
	private boolean checkIfCommentExists(int id) {
		if (this.commentDao.getById(id) == null) {
			return false;
		}
		return true;
	}

}
