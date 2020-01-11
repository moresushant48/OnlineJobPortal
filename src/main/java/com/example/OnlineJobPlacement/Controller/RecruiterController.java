package com.example.OnlineJobPlacement.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.OnlineJobPlacement.Model.Job;
import com.example.OnlineJobPlacement.Model.RecruitmentApplication;
import com.example.OnlineJobPlacement.Model.User;
import com.example.OnlineJobPlacement.Repository.JobRepository;
import com.example.OnlineJobPlacement.Repository.RecruitmentApplicationsRepository;
import com.example.OnlineJobPlacement.Repository.UserRepository;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {
	
	private RecruitmentApplication recruiter;
	private User user;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	RecruitmentApplicationsRepository recruiterRepository;

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
			jobRepository.save(job);
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
	
}
