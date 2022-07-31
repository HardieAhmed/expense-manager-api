package com.hardieahmed.expensetrackerapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hardieahmed.expensetrackerapi.entity.User;
import com.hardieahmed.expensetrackerapi.entity.UserModel;
import com.hardieahmed.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import com.hardieahmed.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.hardieahmed.expensetrackerapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	UserRepository userRepository;

	@Override
	public User createUser(UserModel user) {

		if (userRepository.existsByEmail(user.getEmail())) {
			throw new ItemAlreadyExistsException(
					"This email '" + user.getEmail() + "' is already used by another user.");
		}

		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	@Override
	public User readUser() {
		Long userId = getLoggedInUser().getId();
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User was not found for this id"));
	}

	@Override
	public User updateUser(UserModel user) {
		User existingUser = readUser();

		existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
		existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
		existingUser.setPassword(
				user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
		existingUser.setAge((user.getAge() != null && user.getAge() != 0) ? user.getAge() : existingUser.getAge());

		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser() {
		User existingUser = readUser();
		userRepository.delete(existingUser);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for the email '" + email + "'"));
	}

}
