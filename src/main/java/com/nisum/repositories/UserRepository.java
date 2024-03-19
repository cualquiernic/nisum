package com.nisum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nisum.entities.User;


/**
 * Clase repositorio para user
 * @author Cristian Altamirano LLanos
 */
@Repository
public interface UserRepository extends JpaRepository<User,String>{
	
	@Query(value = "Select u FROM User u WHERE u.email = :email", nativeQuery = false)
	public User getByEmail(@Param("email") String email);


}
