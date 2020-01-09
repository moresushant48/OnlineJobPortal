package com.example.OnlineJobPlacement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.OnlineJobPlacement.Model.RecruitmentApplication;
import com.example.OnlineJobPlacement.Model.RecruitmentApplication.StatusConstants;
import com.example.OnlineJobPlacement.Repository.RecruitmentApplicationsRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RecruitmentApplicationsRepository recruitementApplicationRepository;

	@GetMapping("/partnership")
	public ModelAndView partnershipGET() {
		ModelAndView mv = new ModelAndView("admin");
		
		List<RecruitmentApplication> items = recruitementApplicationRepository.findAll(Sort.by(Order.desc("id")));
		mv.addObject("requests", items);
				
		return mv;
	}
	
	@GetMapping("/partnership/approve")
	public ModelAndView approveGET(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView("redirect:/admin/partnership");
		
		RecruitmentApplication ra = recruitementApplicationRepository.getOne(id);
		ra.setStatus(StatusConstants.APPROVED);
		recruitementApplicationRepository.save(ra);
		
		return mv;
	}
	
	@GetMapping("/partnership/deny")
	public ModelAndView denyGET(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView("redirect:/admin/partnership");
		
		RecruitmentApplication ra = recruitementApplicationRepository.getOne(id);
		ra.setStatus(StatusConstants.DENIED);
		recruitementApplicationRepository.save(ra);
		
		return mv;
	}
	
}
