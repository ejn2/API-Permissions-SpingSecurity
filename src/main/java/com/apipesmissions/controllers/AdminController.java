package com.apipesmissions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apipesmissions.dto.RoleDTO;
import com.apipesmissions.exceptions.RoleAlreadyExistsException;
import com.apipesmissions.exceptions.RoleNotFoundException;
import com.apipesmissions.exceptions.UserNotFoundException;
import com.apipesmissions.services.RoleService;

@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController {

	@Autowired
	private RoleService roleService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RoleDTO saveRole(@RequestBody RoleDTO roleDTO) throws RoleAlreadyExistsException {
		return this.roleService.saveRole(roleDTO);
	}
	
	@PatchMapping(path = "/{id}")
	public void addRoleToUser(@PathVariable Long id, @RequestBody RoleDTO roleDTO) throws RoleNotFoundException, UserNotFoundException, RoleAlreadyExistsException {
		this.roleService.addRoleToUser(id, roleDTO.getName());
	}
	
	
	@DeleteMapping(path="/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteRole(@PathVariable Long id) throws RoleNotFoundException {
		this.roleService.deleteRoleById(id);
	}
	
}
