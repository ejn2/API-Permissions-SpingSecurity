package com.apipesmissions.utils;

import com.apipesmissions.dto.CreateUserDTO;
import com.apipesmissions.dto.RoleDTO;
import com.apipesmissions.dto.UserDTO;
import com.apipesmissions.models.RoleModel;
import com.apipesmissions.models.UserModel;

public class TestUtils extends RenderModel {
	
	public RoleModel roleModelBuild() {
		
		RoleModel role = new RoleModel();
		role.setId(1L);
		role.setName("USER");
		
		return role;
	}
	
	
	public RoleDTO roleDTOBuild() {
		return this.convertRoleToDto(this.roleModelBuild());
	}

	public UserModel userBuild() {
		UserModel user = new UserModel();
			
		user.setId(1L);
		user.setName("User-01");
		user.setUsername("user001");
		user.setPassword("123");

		return user;
	}
	
	public UserDTO userDtoBuild() {
		return this.convertToDto(this.userBuild());
	}
	
	public CreateUserDTO createUserDtoBuild() {
		CreateUserDTO createUser = new CreateUserDTO();
		
		createUser.setName(this.userBuild().getName());
		createUser.setUsername(this.userBuild().getUsername());
		createUser.setPassword(this.userBuild().getPassword());
		
		return createUser;
	}
}
