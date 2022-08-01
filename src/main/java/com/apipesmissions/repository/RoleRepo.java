package com.apipesmissions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apipesmissions.models.RoleModel;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel, Long>{

	Optional<RoleModel> findByName(String roleName);
	
}