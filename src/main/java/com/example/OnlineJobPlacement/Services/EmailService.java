package com.example.OnlineJobPlacement.Services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.OnlineJobPlacement.Model.Job;

@Service
public class EmailService {

	@Value(value = "${spring.mail.username}")
	private String myEmail;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String emailTo, Job job) {
				
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			
			mimeMessageHelper.setFrom(myEmail);
			mimeMessageHelper.setTo("dhondesooraj@gmail.com");
			mimeMessageHelper.setSubject("Job Selection at OnlineJobPlacement.");
			mimeMessageHelper.setText("<b style=\"color:green;\">Congrats..! You are eligible for the following.</b>"
					+ "<br>Company Name : " + job.getRecruitmentApplication().getCompanyName()
					+ "<br>Company Website : " + job.getRecruitmentApplication().getWebsite()
					+ "<br>Designation: " + job.getJobPost()
					+ "<br>Technology : " + job.getTech()
					+ "<br><br>Please visit OnlineJobPlacement portal for further status. Company will email you for further selection process."
					+ "<br><br><small>This is an auto-generated email. Do not reply.</small>", true);
			
			javaMailSender.send(mimeMessage);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getCause());
		}
		
	}
}