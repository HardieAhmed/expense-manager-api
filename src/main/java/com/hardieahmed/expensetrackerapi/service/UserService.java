package com.hardieahmed.expensetrackerapi.service;

import com.hardieahmed.expensetrackerapi.entity.User;
import com.hardieahmed.expensetrackerapi.entity.UserModel;

public interface UserService {
	
	User createUser(UserModel user);
	User readUser();
	User updateUser(UserModel user);
	void deleteUser();
	User getLoggedInUser();
}
