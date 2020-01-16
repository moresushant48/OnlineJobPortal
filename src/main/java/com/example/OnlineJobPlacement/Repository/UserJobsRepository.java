package com.example.OnlineJobPlacement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.OnlineJobPlacement.Model.UserJobs;

@Repository
public interface UserJobsRepository extends JpaRepository<UserJobs, Long> {
	
	public boolean existsByJobJobIdAndUserId(Long jobId, Long userId);

	public UserJobs findByJobJobIdAndUserId(Long jobId, Long userId);
	
	public void deleteByJobJobIdAndUserId(Long jobId, Long userId);
	
	@Query(value = "SELECT * FROM user_jobs uj INNER JOIN tb_user u ON(uj.user_id = u.user_id) WHERE job_id = ?1", nativeQuery = true)
	public List<UserJobs> listUsersForOneJob(Long jobId);
	
	@Query(value = "SELECT distinct uj.job_id, uj.user_id, uj.user_jobs_id, uj.job_status, j.job_id, j.job_post, j.tech, j.job_details, r.company_name, r.website FROM user_jobs uj INNER JOIN job j ON(uj.job_id = j.job_id) INNER JOIN recruitment_applications r ON(r.recruiter_id = j.recruiter_id) WHERE uj.user_id = ?1", nativeQuery = true)
	public List<UserJobs> listJobsForOneUser(Long userId);
}
