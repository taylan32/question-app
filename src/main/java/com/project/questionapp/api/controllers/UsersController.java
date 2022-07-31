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

import com.project.questionapp.business.abstracts.UserService;
import com.project.questionapp.business.responses.UserResponse;
import com.project.questionapp.core.utilities.results.DataResult;
import com.project.questionapp.core.utilities.results.Result;
import com.project.questionapp.entities.User;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UsersController {
	
	private UserService userService;
	
	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/")
	public ResponseEntity<?> add(@RequestBody User user) throws Exception {
		return ResponseEntity.ok(this.userService.add(user));
	}
	
	@GetMapping("/getAll")
	public DataResult<List<UserResponse>> getAll() {
		return this.userService.getAll();
	}
	
	@GetMapping("/{id}")
	public DataResult<UserResponse> getById(@PathVariable("id") int id) throws Exception {
		return this.userService.getOneUserById(id);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody User user) throws Exception {
		return ResponseEntity.ok(this.userService.update(user));
	}
	
	@PostMapping("/{id}/delete")
	public Result delete(@PathVariable("id") int id) throws Exception {
		return this.userService.delete(id);
	}
	
	@GetMapping("/activity/{userId}")
	public DataResult<List<Object>> getUserActivity(@PathVariable int userId){
		return this.userService.getUserActivity(userId);
	}
	
	@PostMapping("/changeAvatar")
	public Result changeAvatar(@RequestParam("userId") int userId, @RequestParam("avatar") int avatar) throws Exception {
		return this.userService.changeAvatar(userId, avatar);

	}
	
	
}
