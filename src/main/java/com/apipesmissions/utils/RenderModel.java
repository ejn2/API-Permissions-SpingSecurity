package com.apipesmissions.utils;

import org.modelmapper.ModelMapper;

import com.apipesmissions.dto.CreateUserDTO;
import com.apipesmissions.dto.RoleDTO;
import com.apipesmissions.dto.UserDTO;
import com.apipesmissions.models.RoleModel;
import com.apipesmissions.models.UserModel;

public abstract class RenderModel {

	private ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	protected UserDTO convertToDto(UserModel userModel) {
		return this.modelMapper().map(userModel, UserDTO.class);
	}
	
	protected UserModel convertToModel(UserDTO userDTO) {
		return this.modelMapper().map(userDTO, UserModel.class);
	}
	
	protected UserModel createUserToUserModel(CreateUserDTO createUserDto) {
		return this.modelMapper().map(createUserDto, UserModel.class);
	}
	
	protected RoleDTO convertRoleToDto(RoleModel roleModel) {
		return this.modelMapper().map(roleModel, RoleDTO.class);
	}
	
	protected RoleModel convertRoleToModel(RoleDTO roleDTO) {
		return this.modelMapper().map(roleDTO, RoleModel.class);
	}
}
