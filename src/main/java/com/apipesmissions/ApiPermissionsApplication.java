package com.apipesmissions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apipesmissions.models.RoleModel;
import com.apipesmissions.repository.RoleRepo;

@SpringBootApplication
public class ApiPermissionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPermissionsApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepo roleRepo) {
		
		return args -> {
			
			RoleModel role = new RoleModel("USER");
		
			roleRepo.save(role);
			
		};
		
	}
	
}
