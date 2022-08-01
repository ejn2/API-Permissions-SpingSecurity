package com.apipesmissions.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apipesmissions.dto.CreateUserDTO;
import com.apipesmissions.dto.UserDTO;
import com.apipesmissions.exceptions.RoleNotFoundException;
import com.apipesmissions.exceptions.UserAlreadyExistsException;
import com.apipesmissions.exceptions.UserNotFoundException;
import com.apipesmissions.services.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public List<UserDTO> listAllUsers() {
		return this.userService.listAllUsers();
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserDTO saveUser(@RequestBody CreateUserDTO user) throws UserAlreadyExistsException, RoleNotFoundException, UserNotFoundException {
		return this.userService.saveUser(user);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
		this.userService.deleteUser(id);
	}
	
}
