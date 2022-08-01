package com.apipesmissions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apipesmissions.models.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long>{

	Optional<UserModel> findByUsername(String username);
	
}
