package com.project.questionapp.api.controllers;

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

import com.project.questionapp.business.abstracts.PostService;
import com.project.questionapp.business.requests.post.CreatePostRequest;
import com.project.questionapp.business.requests.post.UpdatePostRequest;
import com.project.questionapp.business.responses.PostResponse;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostsController {

	private PostService postService;

	@Autowired
	public PostsController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/")
	public DataResult<List<PostResponse>> getAll() {
		return this.postService.getAll();
	}

	@GetMapping("/getByUserId")
	public DataResult<List<PostResponse>> getPostsByUserId(@RequestParam("userId") int userId) throws Exception {
		return this.postService.getPostsByUserId(userId);
	}

	@GetMapping("/{id}")
	public DataResult<PostResponse> getById(@PathVariable("id") int id) throws Exception {
		return this.postService.getOnePostById(id);
	}

	@PostMapping("/")
	public ResponseEntity<Object> add(@Valid @RequestBody CreatePostRequest postRequest) throws Exception {

		return ResponseEntity.ok(this.postService.add(postRequest));
	}

	@PostMapping("/{id}/update")
	public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody UpdatePostRequest updateRequest)
			throws Exception {
		return ResponseEntity.ok(this.postService.update(id, updateRequest));
	}

	@PostMapping("/{id}/delete")
	public Result delete(@PathVariable("id") int id) throws Exception {
		return this.postService.delete(id);
	}

}
