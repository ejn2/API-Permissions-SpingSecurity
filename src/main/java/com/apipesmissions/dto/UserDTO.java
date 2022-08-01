package com.apipesmissions.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {

	private Long id;
	private String name;
	private String username;
	private List<RoleDTO> roles = new ArrayList<>();
	
}