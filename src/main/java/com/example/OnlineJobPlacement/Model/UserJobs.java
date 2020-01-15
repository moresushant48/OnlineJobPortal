package com.example.OnlineJobPlacement.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_jobs")
public class UserJobs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_jobs_id")
	private Long userJobsId;
	
	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
		
	public UserJobs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserJobs(Job job, User user) {
		super();
		this.job = job;
		this.user = user;
	}

	public Long getUserJobsId() {
		return userJobsId;
	}

	public void setUserJobsId(Long userJobsId) {
		this.userJobsId = userJobsId;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
