package com.example.OnlineJobPlacement.Controller;

import java.security.Principal;

import javax.transaction.Transactional;

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

import com.example.OnlineJobPlacement.Model.RecruitmentApplication;
import com.example.OnlineJobPlacement.Model.RecruitmentApplication.StatusConstants;
import com.example.OnlineJobPlacement.Model.User;
import com.example.OnlineJobPlacement.Repository.JobRepository;
import com.example.OnlineJobPlacement.Repository.RecruitmentApplicationsRepository;
import com.example.OnlineJobPlacement.Repository.UserJobsRepository;
import com.example.OnlineJobPlacement.Repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private User user;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	UserJobsRepository userJobsRepository;
	
	@Autowired
	RecruitmentApplicationsRepository recruitmentApplicationsRepository;

	@GetMapping("/partnershipForm")
	public ModelAndView partnershipFormGET(Model model, Principal principal) {
		ModelAndView mv = new ModelAndView("partnershipForm");
		model.addAttribute("partnershipFormData", new RecruitmentApplication());
		
		user = userRepository.findByEmail(principal.getName());
		mv.addObject("userEmail", principal.getName());
		return mv;
	}
	
	@PostMapping("/partnershipForm")
	public ModelAndView partnershipFormPOST(@ModelAttribute("partnershipFormData") RecruitmentApplication recruitmentApplication, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("partnershipForm");
		
		if(recruitmentApplicationsRepository.getPartnershipDataFromUserId(user.getId()) != null ) {
			System.out.println("Applied.");
			mv.addObject("result", "You already have a Company partnered with us.");
			return mv;
		}else System.out.println("Not applied.");
		
		if(!bindingResult.hasErrors()) {
			recruitmentApplication.setUser(user);
			recruitmentApplication.setEmail(user.getEmail());
			recruitmentApplication.setStatus(StatusConstants.UNCHECKED);
			if(recruitmentApplicationsRepository.save(recruitmentApplication) != null) {
					mv.addObject("result", "Successfully Applied for Partnership verification.");
				} else mv.addObject("result", "Could't apply for Partnership verification.");
		}
		return mv;
	}
	
	@GetMapping("/account")
	public ModelAndView accountGET(Principal principal) {
		ModelAndView mv = new ModelAndView("userAccount");
		
		user = userRepository.findByEmail(principal.getName());
		mv.addObject("user", user);
		
		return mv;
	}
	
	@PostMapping("/account/update/resume")
	public ModelAndView updateResume(@RequestParam("resume") String resume) {
		ModelAndView mv = new ModelAndView("redirect:/user/account");
		
		user.setResume(resume);
		userRepository.save(user);
		
		return mv;
	}
	
	@GetMapping("/myJobs")
	public ModelAndView myjobsGET(Principal principal) {
		ModelAndView mv = new ModelAndView("userListAppliedJobs");
		
		user = userRepository.findByEmail(principal.getName());
		mv.addObject("userJobs", userJobsRepository.listJobsForOneUser(user.getId()));
		
		return mv;
	}
	
	@Transactional
	@GetMapping("/myJobs/cancel")
	public ModelAndView myjobsCancelGET(@RequestParam("jobId") Long jobId) {
		ModelAndView mv = new ModelAndView("redirect:/user/myJobs");
		
		userJobsRepository.deleteByJobJobIdAndUserId(jobId, user.getId());
		
		return mv;
	}
}
