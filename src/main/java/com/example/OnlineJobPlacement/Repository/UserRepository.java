package com.example.OnlineJobPlacement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.OnlineJobPlacement.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "SELECT * FROM tb_user WHERE email = ?1", nativeQuery = true)
	public User findByEmail(String email);
	
}
