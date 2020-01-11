package com.example.OnlineJobPlacement.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.OnlineJobPlacement.Model.Job;
import com.example.OnlineJobPlacement.Model.RecruitmentApplication;
import com.example.OnlineJobPlacement.Model.User;
import com.example.OnlineJobPlacement.Model.RecruitmentApplication.StatusConstants;
import com.example.OnlineJobPlacement.Model.Role;
import com.example.OnlineJobPlacement.Repository.JobRepository;
import com.example.OnlineJobPlacement.Repository.RecruitmentApplicationsRepository;
import com.example.OnlineJobPlacement.Repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	RecruitmentApplicationsRepository recruitementApplicationRepository;

	@GetMapping("/partnership")
	public ModelAndView partnershipGET() {
		ModelAndView mv = new ModelAndView("adminPartnership");
		
		List<RecruitmentApplication> items = recruitementApplicationRepository.findAll(Sort.by(Order.desc("id")));
		mv.addObject("requests", items);
				
		return mv;
	}
	
	@GetMapping("/partnership/approve")
	public ModelAndView approveGET(@RequestParam("statusId") Long statusId, @RequestParam("userId") Long userId) {
		ModelAndView mv = new ModelAndView("redirect:/admin/partnership");
		
		RecruitmentApplication ra = recruitementApplicationRepository.getOne(statusId);
		ra.setStatus(StatusConstants.APPROVED);
		if(recruitementApplicationRepository.save(ra) != null) {
			User user = userRepository.getOne(userId);
			user.setRole(new Role(2l));
			userRepository.save(user);
		}
		
		return mv;
	}
	
	@GetMapping("/partnership/deny")
	public ModelAndView denyGET(@RequestParam("statusId") Long statusId, @RequestParam("userId") Long userId) {
		ModelAndView mv = new ModelAndView("redirect:/admin/partnership");
		
		RecruitmentApplication ra = recruitementApplicationRepository.getOne(statusId);
		ra.setStatus(StatusConstants.DENIED);
		if(recruitementApplicationRepository.save(ra) != null) {
			User user = userRepository.getOne(userId);
			user.setRole(new Role(3l));
			userRepository.save(user);
		}
		
		return mv;
	}
	
	@GetMapping("/listUsers")
	public ModelAndView listAllUsers() {
		ModelAndView mv = new ModelAndView("adminListUsers");
		
		List<User> userList = userRepository.findAll();
		mv.addObject("users", userList);
		
		return mv;		
	}
	
	@GetMapping("/listUsers/delete")
	public ModelAndView deleteUser(@RequestParam("userId") Long userId) {

		userRepository.deleteById(userId);
		return new ModelAndView("redirect:/admin/listUsers");
	}
	
	@GetMapping("/listJobs")
	public ModelAndView getJobsList(Principal principal) {
		ModelAndView mv = new ModelAndView("adminListJobs");
		
		User user = userRepository.findByEmail(principal.getName());
		List<Job> list = jobRepository.listJobsByAdmin(user.getId());
		mv.addObject("jobs", list);
		
		return mv;
	}
	
	@GetMapping("/listJobs/delete")
	public ModelAndView deleteJob(@RequestParam("jobId") Long jobId) {
		ModelAndView mv = new ModelAndView("redirect:/admin/listJobs");
		
		jobRepository.deleteById(jobId);
		
		return mv;
	}
}
