package com.example.OnlineJobPlacement.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RecruitmentApplications")
public class RecruitmentApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recruiter_id")
	private Long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "companyName")
	private String companyName;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "status")
	private String status;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static class StatusConstants {
		public final static String APPROVED = "APPROVED";
		public final static String DENIED = "DENIED";
		public final static String UNCHECKED = "UNCHECKED";
	}
}
