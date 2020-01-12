package com.example.OnlineJobPlacement.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.OnlineJobPlacement.Model.Job;
import com.example.OnlineJobPlacement.Repository.JobRepository;
import com.example.OnlineJobPlacement.Repository.UserRepository;

@Controller
public class JobsController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@GetMapping("/jobs")
	public ModelAndView listJobs() {
		ModelAndView mv = new ModelAndView("jobs");
		
		List<Job> jobs = jobRepository.findAll();
		mv.addObject("jobs", jobs);
		
		return mv;
	}
	
	@GetMapping("/jobs/apply")
	public ModelAndView applyForJob(@RequestParam("jobId") Long jobId, Principal principal) {
		ModelAndView mv = new ModelAndView("");
		
		// Will code it later.
		
		return mv;
	}
	
}
