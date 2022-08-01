package com.apipesmissions.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.apipesmissions.dto.RoleDTO;
import com.apipesmissions.models.RoleModel;
import com.apipesmissions.models.UserModel;
import com.apipesmissions.repository.RoleRepo;
import com.apipesmissions.repository.UserRepo;
import com.apipesmissions.services.RoleService;
import com.apipesmissions.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AdminController.class)
public class AdminControllerTest {

	private final String API_URL = "http://localhost:8080/api/v1/admin";
	
	@MockBean
	RoleRepo roleRepo;
	
	@MockBean
	RoleService roleService;
	
	@MockBean
	UserRepo userRepo;
	
	@Autowired
	MockMvc mockMvc;
	
	RoleModel roleModel;
	RoleDTO roleDTO;
	
	UserModel userModel;
	
	@BeforeEach
	void setup() {
		TestUtils testUtils = new TestUtils();
		
		this.roleModel = testUtils.roleModelBuild();
		this.roleDTO = testUtils.roleDTOBuild();
		
		this.userModel = testUtils.userBuild();
		
	}
	
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Test
	void saveRoleTest() throws Exception {
		
		when(this.roleService.saveRole(Mockito.any(RoleDTO.class)))
			.thenReturn(this.roleDTO);
		
		String body = this.objectMapper().writeValueAsString(this.roleDTO);
		
		this.mockMvc.perform(post(this.API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", is(this.roleDTO.getName())));
		
	}
	
	@Test
	void addRoleToUserTest() throws Exception {
		
		String url = String.format("%s/%d", this.API_URL, this.roleDTO.getId());
		
		String body = this.objectMapper().writeValueAsString(this.roleDTO);
		
		this.mockMvc.perform(patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isOk());
		
	}
	
	
	@Test
	void deleteRoleTest() throws Exception {
		String url = String.format("%s/%d", this.API_URL, this.roleDTO.getId());
		
		this.mockMvc.perform(delete(url))
			.andExpect(status().isNoContent());
	}
	
	
}
