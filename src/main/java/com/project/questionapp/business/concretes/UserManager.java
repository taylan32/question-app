package com.project.questionapp.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.questionapp.business.abstracts.UserService;
import com.project.questionapp.business.constants.Messages;
import com.project.questionapp.business.responses.UserResponse;
import com.project.questionapp.core.exceptions.AlreadyExistsException;
import com.project.questionapp.core.exceptions.NotFoundException;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.ErrorDataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.core.utilities.results.SuccessDataResult;
import com.project.questionapp.core.utilities.results.SuccessResult;
import com.project.questionapp.dataAccess.CommentDao;
import com.project.questionapp.dataAccess.LikeDao;
import com.project.questionapp.dataAccess.PostDao;
import com.project.questionapp.dataAccess.UserDao;
import com.project.questionapp.entities.User;

@Service
public class UserManager implements UserService {

	private UserDao userDao;
	private PostDao postDao;
	private CommentDao commentDao;
	private LikeDao likeDao;

	@Autowired
	public UserManager(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao) {
		this.userDao = userDao;
		this.postDao = postDao;
		this.commentDao = commentDao;
		this.likeDao = likeDao;
	}

	@Override
	public Result add(User user) throws Exception {

		if (this.userDao.getByUserName(user.getUserName()) != null) {
			throw new AlreadyExistsException(Messages.userAlreadyExists);
		}
		user.setCreatedAt(new java.util.Date());
		this.userDao.save(user);
		return new SuccessResult(Messages.userAdded);

	}

	@Override
	public Result delete(int id) throws Exception {

		if (!checkIfUserExists(id)) {
			throw new NotFoundException(Messages.userNotFound);
		}
		User userToDelete = this.getById(id).getData();
		this.userDao.delete(userToDelete);
		return new SuccessResult(Messages.userDeleted);

	}

	@Override
	public Result update(User user) throws Exception {
		if (!checkIfUserExists(user.getId())) {
			throw new NotFoundException(Messages.userNotFound);
		}
		this.userDao.save(user);
		return new SuccessResult(Messages.userUpdated);
	}

	@Override
	public DataResult<User> getById(int id) throws Exception {
		if (!checkIfUserExists(id)) {
			throw new NotFoundException(Messages.userNotFound);
		}
		return new SuccessDataResult<User>(this.userDao.getById(id), Messages.userListed);
	}

	@Override
	public DataResult<List<UserResponse>> getAll() {
		// return new SuccessDataResult<List<User>>(this.userDao.findAll(),
		// Messages.userListed);
		List<User> users = this.userDao.findAll();
		return new SuccessDataResult<List<UserResponse>>(
				users.stream().map(user -> new UserResponse(user)).collect(Collectors.toList()), Messages.userListed);

	}

	@Override
	public DataResult<User> getByUserName(String userName) {
		return new SuccessDataResult<User>(this.userDao.getByUserName(userName), Messages.userListed);
	}

	private boolean checkIfUserExists(int id) {
		if (this.userDao.getById(id) == null) {
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public DataResult<List<Object>> getUserActivity(int userId) {
		List<Integer> postIds = this.postDao.getTopByUserId(userId);
		if (postIds.isEmpty()) {
			return new ErrorDataResult<List<Object>>(Messages.noPostFound);
		}
		List<Object> comments = this.commentDao.getUserCommentsByPostId(postIds);
		List<Object> likes = this.likeDao.getUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<Object>();
		result.addAll(comments);
		result.addAll(likes);
		return new SuccessDataResult<List<Object>>(result, Messages.userActivityListed);
	}

	@Override
	public DataResult<UserResponse> getOneUserById(int userId) throws Exception {
		if (!checkIfUserExists(userId)) {
			throw new NotFoundException(Messages.userNotFound);
		}
		User user = this.userDao.getById(userId);
		return new SuccessDataResult<UserResponse>(new UserResponse(user), Messages.userListed);
	}

	@Override
	public Result changeAvatar(int userId, int avatar) throws Exception {
		if (!checkIfUserExists(userId)) {
			throw new NotFoundException(Messages.userNotFound);
		}	
		User user = this.userDao.getById(userId);
		user.setAvatar(avatar);
		this.userDao.save(user);
		return new SuccessResult(Messages.userUpdated);
	}

}
