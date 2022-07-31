package com.hardieahmed.expensetrackerapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardieahmed.expensetrackerapi.entity.AuthModel;
import com.hardieahmed.expensetrackerapi.entity.JwtResponse;
import com.hardieahmed.expensetrackerapi.entity.User;
import com.hardieahmed.expensetrackerapi.entity.UserModel;
import com.hardieahmed.expensetrackerapi.security.CustomUserDetailsService;
import com.hardieahmed.expensetrackerapi.service.UserService;
import com.hardieahmed.expensetrackerapi.util.JwtTokenUtil;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {

		authenticate(authModel.getEmail(), authModel.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("User is disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad credentials");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
	}
}
