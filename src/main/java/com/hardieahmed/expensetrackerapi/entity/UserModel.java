package com.hardieahmed.expensetrackerapi.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserModel {
	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Please enter a valid email")
	private String email;
	
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 5, message = "Passowrd should be at least 5 characters")
	private String password;
	
	private Long age = 0L;

}
