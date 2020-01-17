package com.example.OnlineJobPlacement.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.OnlineJobPlacement.Model.Job;
import com.example.OnlineJobPlacement.Model.RecruitmentApplication;
import com.example.OnlineJobPlacement.Model.User;
import com.example.OnlineJobPlacement.Model.UserJobs;
import com.example.OnlineJobPlacement.Model.UserJobs.JobStatus;
import com.example.OnlineJobPlacement.Repository.JobRepository;
import com.example.OnlineJobPlacement.Repository.RecruitmentApplicationsRepository;
import com.example.OnlineJobPlacement.Repository.UserJobsRepository;
import com.example.OnlineJobPlacement.Repository.UserRepository;
import com.example.OnlineJobPlacement.Services.EmailService;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {
	
	private RecruitmentApplication recruiter;
	private User user;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	RecruitmentApplicationsRepository recruiterRepository;
	
	@Autowired
	UserJobsRepository userJobsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/addJobs")
	public ModelAndView addJobsGET(Model model, Principal principal) {
		ModelAndView mv = new ModelAndView("recruiterAddJobs");
		model.addAttribute("addJobsData", new Job());
		
		user = userRepository.findByEmail(principal.getName());
		recruiter = recruiterRepository.findRecruiterByUserId(user.getId());
		
		return mv;
	}
	
	@PostMapping("/addJobs")
	public ModelAndView addJobsPOST(@ModelAttribute("addJobsData") Job job, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("recruiterAddJobs");
		
		if(!bindingResult.hasErrors()) {
			job.setRecruitmentApplication(recruiter);
			
			if( jobRepository.save(job) != null ) {
				mv.addObject("result", "Added Job.");
			} else mv.addObject("result", "Couldn't Add Job.");
		}
		
		return mv;
	}
	
	@GetMapping("/getJobsList")
	public ModelAndView getJobsList(Principal principal) {
		ModelAndView mv = new ModelAndView("recruiterGetJobsList");
		
		user = userRepository.findByEmail(principal.getName());
		List<Job> list = jobRepository.listJobsByRecruiter(user.getId());
		mv.addObject("jobs", list);
		
		return mv;
	}
	
	@GetMapping("/getJobsList/delete")
	public ModelAndView deleteJob(@RequestParam("jobId") Long jobId) {
		ModelAndView mv = new ModelAndView("redirect:/recruiter/getJobsList");
		
		jobRepository.deleteById(jobId);
		
		return mv;
	}
	
	@GetMapping("/job/{jobId}/users")
	public ModelAndView listUsersForJob(@PathVariable("jobId") Long jobId) {
		ModelAndView mv = new ModelAndView("recruiterListUsersForJob");
		
		mv.addObject("job", jobRepository.getOne(jobId));
		
		List<UserJobs> users = userJobsRepository.listUsersForOneJob(jobId);
		mv.addObject("users", users);
		
		return mv;
	}
	
	@GetMapping("/job/{jobId}/user")
	public ModelAndView setJobStatusOfUser(@PathVariable("jobId") Long jobId, @RequestParam("userId") Long userId, @RequestParam("statusId") int statusId) {
		ModelAndView mv = new ModelAndView("redirect:/recruiter/job/" + jobId + "/users");
		UserJobs userJobs = userJobsRepository.findByJobJobIdAndUserId(jobId, userId);
				
		if(statusId == 1) {
			userJobs.setJobStatus(JobStatus.SELECTED);
			User user = userRepository.getOne(userId);
			Job job = jobRepository.getOne(jobId);
			
			emailService.sendEmail(user.getEmail(), job);
		}
		else if(statusId == 0) userJobs.setJobStatus(JobStatus.NOTSELECTED);
		
		userJobsRepository.save(userJobs);
		
		return mv;
	}
}
