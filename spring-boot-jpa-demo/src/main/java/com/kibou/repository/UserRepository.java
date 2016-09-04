package com.kibou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kibou.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	//User findById(Long id); // == findOne(Serializable)
	
	User findByName(String name);
	
	@Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);
}
