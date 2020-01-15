package com.example.OnlineJobPlacement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.OnlineJobPlacement.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "SELECT * FROM tb_user WHERE email = ?1", nativeQuery = true)
	public User findByEmail(String email);
	
	@Query(value = "SELECT u.user_id, u.username, u.email, u.password, u.resume, u.role_id, uj.job_id FROM tb_user u INNER JOIN user_jobs uj ON(u.user_id = uj.user_id) WHERE job_id = ?1", nativeQuery = true)
	public List<User> listUsersForOneJob(Long jobId);
	
}
