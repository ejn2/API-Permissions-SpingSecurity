package com.apipesmissions.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apipesmissions.dto.RoleDTO;
import com.apipesmissions.exceptions.RoleAlreadyExistsException;
import com.apipesmissions.exceptions.RoleNotFoundException;
import com.apipesmissions.exceptions.UserNotFoundException;
import com.apipesmissions.models.RoleModel;
import com.apipesmissions.models.UserModel;
import com.apipesmissions.repository.RoleRepo;
import com.apipesmissions.repository.UserRepo;
import com.apipesmissions.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

	@Mock
	RoleRepo roleRepo;
	
	@Mock
	UserRepo userRepo;
	
	@InjectMocks
	RoleService roleService;
	
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
	
	@Test
	void saveRoleTest() throws RoleAlreadyExistsException {
		
		when(this.roleRepo.findByName(Mockito.anyString()))
			.thenReturn(Optional.empty());
		
		when(this.roleRepo.save(Mockito.any(RoleModel.class)))
			.thenReturn(this.roleModel);
		
		RoleDTO createdRole = this.roleService.saveRole(this.roleDTO);
		
		assertEquals(this.roleDTO.getName(),
			createdRole.getName()
				);

	}
	
	@Test
	void findRoleByName() throws RoleNotFoundException {
		
		when(this.roleRepo.findByName(Mockito.anyString()))
			.thenReturn(Optional.of(this.roleModel));
		
		RoleDTO role = this.roleService.findRoleByName(this.roleDTO.getName());
		
		assertEquals(this.roleDTO.getName(),
			role.getName()
				);
		
	}
	
	@Test
	void deleteRoleTest() throws RoleNotFoundException  {
	
		when(this.roleRepo.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(this.roleModel));
		
		doNothing().when(this.roleRepo).deleteById(Mockito.anyLong());
		
		this.roleService.deleteRoleById(this.roleDTO.getId());
		
		verify(this.roleRepo, times(1)).findById(Mockito.anyLong());
		verify(this.roleRepo, times(1)).deleteById(Mockito.anyLong());
		
	}
	
	@Test
	void addRoleToUserTest() throws RoleNotFoundException, UserNotFoundException, RoleAlreadyExistsException {
		
		when(this.roleRepo.findByName(Mockito.anyString()))
			.thenReturn(Optional.of(this.roleModel));
		
		when(this.userRepo.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(this.userModel));
		
		this.roleService.addRoleToUser(this.userModel.getId(), this.roleModel.getName());
		
		verify(this.roleRepo, times(1)).findByName(Mockito.anyString());
		verify(this.userRepo, times(1)).findById(Mockito.anyLong());	
	}
	
}
