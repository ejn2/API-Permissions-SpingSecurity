package com.apipesmissions.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apipesmissions.dto.CreateUserDTO;
import com.apipesmissions.dto.UserDTO;
import com.apipesmissions.exceptions.RoleNotFoundException;
import com.apipesmissions.exceptions.UserAlreadyExistsException;
import com.apipesmissions.exceptions.UserNotFoundException;
import com.apipesmissions.models.RoleModel;
import com.apipesmissions.models.UserModel;
import com.apipesmissions.repository.RoleRepo;
import com.apipesmissions.repository.UserRepo;
import com.apipesmissions.utils.RenderModel;

@Service
public class UserService extends RenderModel {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	private void verifyIfUsernameAlreadyExists(String username) throws UserAlreadyExistsException {
		
		Optional<UserModel> user = this.userRepo.findByUsername(username);
		
		if(user.isPresent()) {
			throw new UserAlreadyExistsException("User already exists");
		}

	}
	
	
	private RoleModel addRoleToNewUser() throws RoleNotFoundException {
		
		Optional<RoleModel> role = this.roleRepo.findByName("USER");
		
		if(!role.isPresent()) {
			throw new RoleNotFoundException("Role not found.");
		}
		
		
		return role.get();
		
	}
	
	public UserDTO findUserById(Long id) throws UserNotFoundException {
		
		UserModel user = this.userRepo.findById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found."));
		
		return this.convertToDto(user);
	}
	
	
	public List<UserDTO> listAllUsers() {
		return this.userRepo.findAll()
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	
	@Transactional(rollbackOn = RoleNotFoundException.class)
	public UserDTO saveUser(CreateUserDTO user) throws UserAlreadyExistsException, RoleNotFoundException, UserNotFoundException {
	
		this.verifyIfUsernameAlreadyExists(user.getUsername());
		
		UserModel userModel = this.createUserToUserModel(user);
		
		RoleModel userRole = this.addRoleToNewUser();
		userModel.getRoles().add(userRole);
		
		UserModel createdUser = this.userRepo.save(userModel);
		
		return this.convertToDto(createdUser);
		
	}
	
	@Transactional(rollbackOn = UserNotFoundException.class)
	public void deleteUser(Long id) throws UserNotFoundException {
		this.findUserById(id);
		
		this.userRepo.deleteById(id);
		
	}
	
}
