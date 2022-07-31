package com.project.questionapp.business.abstracts;

import java.util.List;

import com.project.questionapp.business.requests.comment.CreateCommentRequest;
import com.project.questionapp.business.requests.comment.UpdateCommentRequest;
import com.project.questionapp.business.responses.CommentResponse;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.entities.Comment;

public interface CommentService {

	Result add(CreateCommentRequest createRequest) throws Exception;

	Result update(int id, UpdateCommentRequest updateRequest) throws Exception;

	Result delete(int id) throws Exception;

	DataResult<List<CommentResponse>> getAllCommentsByPostId(int postId) throws Exception;

	DataResult<List<Comment>> getAllCommentsByUserId(int userId) throws Exception;

	DataResult<Comment> getById(int id) throws Exception;
}
