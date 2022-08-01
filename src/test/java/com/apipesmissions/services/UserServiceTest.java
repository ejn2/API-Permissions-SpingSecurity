package com.apipesmissions.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apipesmissions.dto.CreateUserDTO;
import com.apipesmissions.dto.RoleDTO;
import com.apipesmissions.dto.UserDTO;
import com.apipesmissions.exceptions.RoleNotFoundException;
import com.apipesmissions.exceptions.UserAlreadyExistsException;
import com.apipesmissions.exceptions.UserNotFoundException;
import com.apipesmissions.models.RoleModel;
import com.apipesmissions.models.UserModel;
import com.apipesmissions.repository.RoleRepo;
import com.apipesmissions.repository.UserRepo;
import com.apipesmissions.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepo userRepo;
	
	@Mock
	RoleRepo roleRepo;
	
	@InjectMocks
	UserService userService;
	
	UserModel userModel;
	
	UserDTO userDTO;
	
	RoleModel roleModel;
	
	RoleDTO roleDTO;
	
	CreateUserDTO createUserDto;
	
	@BeforeEach
	void setup() {
		
		TestUtils testUtils = new TestUtils();
		
		this.userModel = testUtils.userBuild();
		
		this.userDTO = testUtils.userDtoBuild();
		
		this.createUserDto = testUtils.createUserDtoBuild();
		
		this.roleModel = testUtils.roleModelBuild();
		
		this.roleDTO = testUtils.roleDTOBuild();
	}
	
	@Test
	void listAllUsersTest() {
		
		when(this.userRepo.findAll())
			.thenReturn(Collections.singletonList(this.userModel));
		
		List<UserDTO> listOfUsers = this.userService.listAllUsers();
	
		
		assertEquals(this.userModel.getName(),
				listOfUsers.get(0).getName()
					);
		
		assertEquals(this.userModel.getUsername(),
			listOfUsers.get(0).getUsername()
				);
		
	}
	
	
	@Test
	void saveUserTest() throws UserAlreadyExistsException, RoleNotFoundException, UserNotFoundException {
		
		when(this.userRepo.findByUsername(Mockito.anyString()))
			.thenReturn(Optional.empty());
		
		when(this.userRepo.save(Mockito.any(UserModel.class)))
			.thenReturn(this.userModel);

		when(this.roleRepo.findByName(Mockito.anyString()))
			.thenReturn(Optional.of(this.roleModel));
		
		UserDTO createdUser = this.userService.saveUser(this.createUserDto);
		
		assertEquals(this.createUserDto.getName(),
			createdUser.getName()
				);
		
		assertEquals(this.createUserDto.getUsername(),
				createdUser.getUsername()
					);
		
	}
	
	
	@Test
	void findUserByIdTest() throws UserNotFoundException {
		
		when(this.userRepo.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(this.userModel));
		
		UserDTO user = this.userService.findUserById(this.userDTO.getId());
		
		assertEquals(this.userDTO.getName(),
			user.getName()
				);
		
		assertEquals(this.userDTO.getUsername(),
				user.getUsername()
					);
	}
	
	
	@Test
	void deleteByidTest() throws UserNotFoundException {
		
		when(this.userRepo.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(this.userModel));
		
		doNothing().when(this.userRepo).deleteById(Mockito.anyLong());
		
		this.userService.deleteUser(this.userDTO.getId());
		
		verify(this.userRepo, times(1)).findById(Mockito.anyLong());
		verify(this.userRepo, times(1)).deleteById(Mockito.anyLong());
	}
	
}
