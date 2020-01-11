package com.example.OnlineJobPlacement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.OnlineJobPlacement.Model.RecruitmentApplication;

@Repository
public interface RecruitmentApplicationsRepository extends JpaRepository<RecruitmentApplication, Long>{
	
	@Query(value = "SELECT * FROM recruitment_applications WHERE user_id = ?1", nativeQuery = true)
	public RecruitmentApplication getPartnershipDataFromUserId(Long userId);

	@Query(value = "SELECT * FROM recruitment_applications WHERE user_id = ?1", nativeQuery = true)
	public RecruitmentApplication findRecruiterByUserId(Long userId);
}
