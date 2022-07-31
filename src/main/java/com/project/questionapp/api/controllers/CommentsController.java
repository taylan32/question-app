package com.project.questionapp.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questionapp.business.abstracts.CommentService;
import com.project.questionapp.business.requests.comment.CreateCommentRequest;
import com.project.questionapp.business.requests.comment.UpdateCommentRequest;
import com.project.questionapp.business.responses.CommentResponse;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.entities.Comment;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentsController {

	private CommentService commentService;

	@Autowired
	public CommentsController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/getByUserId")
	public DataResult<List<Comment>> getAllByUserId(@RequestParam int userId)  throws Exception {
		return this.commentService.getAllCommentsByUserId(userId);
	}

	@GetMapping("/getByPostId")
	public DataResult<List<CommentResponse>> getAllByPostId(@RequestParam int postId)  throws Exception {
		return this.commentService.getAllCommentsByPostId(postId);
	}

	@GetMapping("/{id}")
	public DataResult<Comment> getById(@PathVariable("id") int id) throws Exception {
		return this.commentService.getById(id);
	}
	
	@PostMapping("/")
	public ResponseEntity<Object> add(@Valid @RequestBody CreateCommentRequest commentRequest) throws Exception {
		return ResponseEntity.ok(this.commentService.add(commentRequest));
	}
	
	@PostMapping("/{id}/update")
	public ResponseEntity<Object> update(@PathVariable int id ,@Valid @RequestBody UpdateCommentRequest commentRequest) throws Exception {
		return ResponseEntity.ok(this.commentService.update(id, commentRequest));
	}
	
	@PostMapping("/{id}/delete")
	public Result delete(@PathVariable int id) throws Exception {
		return this.commentService.delete(id);
	}
}
