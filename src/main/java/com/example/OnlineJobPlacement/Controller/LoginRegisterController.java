package com.example.OnlineJobPlacement.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginRegisterController {

	@GetMapping("/login")
	public ModelAndView loginGET() {
		return new ModelAndView("login");
	}
	
	@GetMapping("/register")
	public ModelAndView registerGET() {
		return new ModelAndView("register");
	}
	
}
