package com.example.OnlineJobPlacement.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.OnlineJobPlacement.Model.Job;
import com.example.OnlineJobPlacement.Model.User;
import com.example.OnlineJobPlacement.Model.UserJobs;
import com.example.OnlineJobPlacement.Repository.JobRepository;
import com.example.OnlineJobPlacement.Repository.UserJobsRepository;
import com.example.OnlineJobPlacement.Repository.UserRepository;

@Controller
public class JobsController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	UserJobsRepository userJobsRepository;
	
	@GetMapping("/jobs")
	public ModelAndView listJobs() {
		ModelAndView mv = new ModelAndView("jobs");
		
		List<Job> jobs = jobRepository.findAll();
		mv.addObject("jobs", jobs);
		
		return mv;
	}
	
	@GetMapping("/jobs/apply")
	public ModelAndView applyForJob(@RequestParam("jobId") Long jobId, Principal principal) {
		ModelAndView mv = new ModelAndView("redirect:/jobs");
		
		User user = userRepository.findByEmail(principal.getName());
		Job job = jobRepository.getOne(jobId);
		UserJobs userJobs = new UserJobs(job, user);
		
		if(userJobsRepository.existsByJobJobIdAndUserId(jobId, user.getId())) {
			System.out.println("Already applied for the job.");
//			mv.addObject("existJobId", jobId);
//			mv.addObject("existMsg", "Already applied for this job.");
			return mv;
		}
		
		if(userJobsRepository.save(userJobs) != null) System.out.println("Applied for the Job.");
		else System.out.println("Couldn't apply for the Job.");
		
		return mv;
	}
	
}
