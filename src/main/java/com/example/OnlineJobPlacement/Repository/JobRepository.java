package com.example.OnlineJobPlacement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.OnlineJobPlacement.Model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{

	@Query(value = "SELECT j.job_id, j.job_post, j.tech, j.job_details, r.recruiter_id FROM job AS j INNER JOIN recruitment_applications AS r ON(j.recruiter_id=r.recruiter_id) where r.user_id = ?1", nativeQuery = true)
	public List<Job> listJobsByRecruiter(Long userId);
	
	@Query(value = "SELECT j.job_id, j.job_post, j.tech, j.job_details, r.recruiter_id FROM job as j INNER JOIN recruitment_applications as r ON(j.recruiter_id=r.recruiter_id)", nativeQuery = true)
	public List<Job> listJobsByAdmin(Long userId);
	
}
