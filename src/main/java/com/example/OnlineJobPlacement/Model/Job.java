package com.example.OnlineJobPlacement.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "job")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "job_id")
	private Long jobId;
	
	@Column(name = "jobPost")
	private String jobPost;
	
	@Column(name = "tech")
	private String tech;
	
	@Column(name = "jobDetails")
	private String jobDetails;
	
	@ManyToOne
	@JoinColumn(name = "recruiter_id")
	private RecruitmentApplication recruitmentApplication;

	@OneToMany(mappedBy = "job")
	private List<UserJobs> userJobs;
	
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobPost() {
		return jobPost;
	}

	public void setJobPost(String jobPost) {
		this.jobPost = jobPost;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public String getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}

	public RecruitmentApplication getRecruitmentApplication() {
		return recruitmentApplication;
	}

	public void setRecruitmentApplication(RecruitmentApplication recruitmentApplication) {
		this.recruitmentApplication = recruitmentApplication;
	}

	public List<UserJobs> getUserJobs() {
		return userJobs;
	}

	public void setUserJobs(List<UserJobs> userJobs) {
		this.userJobs = userJobs;
	}
}
