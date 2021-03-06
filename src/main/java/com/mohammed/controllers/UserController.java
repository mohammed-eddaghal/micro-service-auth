package com.mohammed.controllers;

import java.util.Collection;

import java.util.HashSet;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohammed.entities.UserEntity;
import com.mohammed.exception.BusinessResourceException;
import com.mohammed.requests.UserRequest;
import com.mohammed.responses.UserResponse;
import com.mohammed.security.SecurityConstants;
import com.mohammed.service.UserService;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(SecurityConstants.SIGN_UP_URL + "/*")
public class UserController {
	@Autowired
	private UserService userService;

	@ApiOperation(value = " method to retrieve all users ")
	@GetMapping("/users")
	public ResponseEntity<Collection<UserResponse>> getAllUsers() {
		Collection<UserEntity> users = userService.getAllUser();
		ModelMapper modelMapper = new ModelMapper();
		Collection<UserResponse> usersResponse = new HashSet<>();
		for (UserEntity user : users) {
			UserResponse userResponse = modelMapper.map(user, UserResponse.class);
			usersResponse.add(userResponse);
		}
		return new ResponseEntity<Collection<UserResponse>>(usersResponse, HttpStatus.FOUND);
	}

	@ApiOperation(value = " methode to create a user")
	@PostMapping("/register")
	@Transactional
	public ResponseEntity<UserResponse> saveUser(
			@Valid @RequestBody @ApiParam("user info to persist") UserRequest userRequest) {
		String password = userRequest.getPassword();
		String confPassword = userRequest.getConfirmPassword();
		if (!password.equals(confPassword))
			throw new BusinessResourceException("PasswordNotConfirmed", "mote de passe incorrect",
					HttpStatus.NOT_ACCEPTABLE);

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
		UserEntity user = userService.saveOrUpdate(userEntity);
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
	}

	@ApiOperation(value = " methode to update a user")
	@PutMapping("/users")
	public ResponseEntity<UserResponse> updateUser(
			@RequestBody @ApiParam("user info to update") UserRequest userRequest) {
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
		UserEntity user = userService.saveOrUpdate(userEntity);
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}

	@ApiOperation(value = " methode to delete a user")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Void> deleteUser(
			@PathVariable(value = "userId", required = true) @ApiParam("user Id (PUBLIC ID)") String userId)
			throws BusinessResourceException {
		userService.deleteUser(userId);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

	@ApiOperation(value = " methode to get user by username and password")
	@PostMapping("/users/login")
	public ResponseEntity<UserResponse> findUserByUserNameAndPassword(@RequestBody UserRequest userRequest) {
		UserEntity userEntity = userService
				.findByUserNameAndPassword(userRequest.getUserName(), userRequest.getPassword()).get();
		ModelMapper modelMapper = new ModelMapper();
		UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.FOUND);
	}

	@ApiOperation(value = " methode to get user by UserId ")
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserResponse> findByUserId(
			@PathVariable(value = "userId") @ApiParam("user Id (PUBLIC ID)") String userId) {
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = userService.findByUserId(userId).get();
		UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.FOUND);
	}

}
