package com.hardieahmed.expensetrackerapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardieahmed.expensetrackerapi.entity.User;
import com.hardieahmed.expensetrackerapi.entity.UserModel;
import com.hardieahmed.expensetrackerapi.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> readUser() {
		return new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
	}
	
	@PutMapping("/profile")
	public ResponseEntity<User> updateUser(@RequestBody UserModel user) {
		return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
	}
	
	@DeleteMapping("/deactivate")
	public ResponseEntity<HttpStatus> deleteUser() {
		userService.deleteUser();
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
