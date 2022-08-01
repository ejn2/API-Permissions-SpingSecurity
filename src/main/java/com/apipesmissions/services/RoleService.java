package com.apipesmissions.services;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apipesmissions.dto.RoleDTO;
import com.apipesmissions.exceptions.RoleAlreadyExistsException;
import com.apipesmissions.exceptions.RoleNotFoundException;
import com.apipesmissions.exceptions.UserNotFoundException;
import com.apipesmissions.models.RoleModel;
import com.apipesmissions.models.UserModel;
import com.apipesmissions.repository.RoleRepo;
import com.apipesmissions.repository.UserRepo;
import com.apipesmissions.utils.RenderModel;

@Service
public class RoleService extends RenderModel{
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	private void verifyIfRoleNameAlreadyExists(String roleName) throws RoleAlreadyExistsException {
		
		if(this.roleRepo.findByName(roleName).isPresent()) {
			throw new RoleAlreadyExistsException("Role already exists.");
		}
		
	}
	
	private RoleModel findRoleById(Long id) throws RoleNotFoundException {
		
		return this.roleRepo.findById(id)
				.orElseThrow(() -> new RoleNotFoundException("Role not found."));
		
	}
	
	
	public RoleDTO findRoleByName(String roleName) throws RoleNotFoundException {
		Optional<RoleModel> role = this.roleRepo.findByName(roleName);
		
		if(!role.isPresent()) {
			throw new RoleNotFoundException("Role not found.");
		}
		
		return this.convertRoleToDto(role.get());
		
	}
	
	
	public RoleDTO saveRole(RoleDTO roleDto) throws RoleAlreadyExistsException {
		this.verifyIfRoleNameAlreadyExists(roleDto.getName());
		
		 RoleModel role = this.convertRoleToModel(roleDto);
		 
		 RoleModel createdRole = this.roleRepo.save(role);
	
		 return this.convertRoleToDto(createdRole);
		 
	}
	
	
	public void deleteRoleById(Long id) throws RoleNotFoundException {
		
		this.findRoleById(id);
		
		this.roleRepo.deleteById(id);
	}
	
	
	public void addRoleToUser(Long userId, String roleName) throws RoleNotFoundException, UserNotFoundException, RoleAlreadyExistsException {
		RoleDTO role = this.findRoleByName(roleName);
		
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found."));
		
		
		for(int count = 0; count < user.getRoles().size(); count ++) {
			if(user.getRoles().get(count).getName().equals(roleName)) {
				throw new RoleAlreadyExistsException("Role already registered.");
			}
		}

		
		user.getRoles().add(this.convertRoleToModel(role));
		
		this.userRepo.save(user);
		
	}
	
}
