package com.example.OnlineJobPlacement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OnlineJobPlacement.Model.UserJobs;

@Repository
public interface UserJobsRepository extends JpaRepository<UserJobs, Long> {
	
	public boolean existsByJobJobIdAndUserId(Long jobId, Long userId);

	public void deleteByJobJobIdAndUserId(Long jobId, Long userId);
}
