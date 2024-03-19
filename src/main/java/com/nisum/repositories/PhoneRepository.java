package com.nisum.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nisum.entities.Phone;

import jakarta.transaction.Transactional;
@Repository
public interface PhoneRepository extends JpaRepository<Phone,Long>{
	
	@Query(value = "DELETE FROM phone p WHERE p.id_user = :idUser", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	@Transactional
	public void deletePhoneByIdUser(@Param("idUser") String idUser);

}
