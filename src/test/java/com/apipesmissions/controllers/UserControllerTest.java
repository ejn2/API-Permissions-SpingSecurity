package com.apipesmissions.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.apipesmissions.dto.CreateUserDTO;
import com.apipesmissions.dto.UserDTO;
import com.apipesmissions.repository.RoleRepo;
import com.apipesmissions.repository.UserRepo;
import com.apipesmissions.services.UserService;
import com.apipesmissions.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	private final String API_URL = "http://localhost:8080/api/v1/user";

	@MockBean
	UserRepo userRepo;
	
	@MockBean
	RoleRepo roleRepo;
	
	@MockBean
	UserService userService;
	
	@Autowired
	MockMvc mockMvc;
	
	UserDTO userDTO;
	
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@BeforeEach
	void setup() {
		TestUtils testUtils = new TestUtils();
		
		this.userDTO = testUtils.userDtoBuild();
		
	}
	
	@Test
	void listAllUsersTest() throws Exception {
		
		when(this.userService.listAllUsers())
			.thenReturn(Collections.singletonList(this.userDTO));
		
		this.mockMvc.perform(get(this.API_URL)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name", is(this.userDTO.getName())))
			.andExpect(jsonPath("$[0].username", is(this.userDTO.getUsername())));
	}
	
	
	@Test
	void saveUserTest() throws Exception {
		
		when(this.userService.saveUser(Mockito.any(CreateUserDTO.class)))
			.thenReturn(this.userDTO);
		
		String data = this.objectMapper().writeValueAsString(this.userDTO);
		
		this.mockMvc.perform(post(this.API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(data))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", is(this.userDTO.getName())))
			.andExpect(jsonPath("$.username", is(this.userDTO.getUsername())));
		
	}
	
	@Test
	void deleteUserTest() throws Exception {
		
		doNothing().when(this.userService).deleteUser(Mockito.anyLong());
		
		String url = String.format("%s/%s", this.API_URL, this.userDTO.getId());
		
		this.mockMvc.perform(delete(url))
			.andExpect(status().isNoContent());
		
		verify(this.userService, times(1)).deleteUser(Mockito.anyLong());
		
	}
	
}
