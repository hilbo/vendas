package com.vendas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vendas.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	//Busca A variavel completa
	List<User> findBynameIgnoreCase(String name);
		
	/*  Busca com ignore case mas o valor deve ser completo
	@Query("select u from User u where lower (u.name) like lower (?1)")
	List<User> findByEmail(String email);
	*/
	
	// Busca em qualquer lugar porem é case sensitive
	//@Query("from User where UPPER (email) like (%?1%)")
	@Query("select u from User u where lower (u.email) like %?1%")
	List<User> findByemail(String email);
	
	//Busca em qualquer lugar e ignore case
	List<User> findByEmailContainingIgnoreCase(String email);
	List<User> findByNameContainingIgnoreCase(String name);
}
