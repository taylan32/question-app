package com.project.questionapp.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questionapp.business.abstracts.LikeService;
import com.project.questionapp.business.requests.post.CreateLikeRequest;
import com.project.questionapp.core.utilities.results.Result;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin
public class LikesController {
	
	private LikeService likeService;
	
	@Autowired
	public LikesController(LikeService likeService) {
		this.likeService = likeService;
	}

	@GetMapping("/{postId}")
	public Integer getLikeCount(@PathVariable("postId") int postId) throws Exception{
		return this.likeService.countLikesByPostId(postId);
	}
	
	@PostMapping("/removeLike")
	public Result removeLike(@RequestParam int userId, @RequestParam int postId ) throws Exception{
		return this.likeService.delete(userId, postId);
	}
	
	@PostMapping("/")
	public Result likePost(@Valid @RequestBody CreateLikeRequest likeRequest) throws Exception{
		return this.likeService.add(likeRequest);
	}
	
	@GetMapping("/checkIfLiked")
	public boolean checkIfUserLikedPost(@RequestParam int userId, @RequestParam int postId) {
		return this.likeService.checkIfUserLikesPost(userId, postId);
	}
	
}
