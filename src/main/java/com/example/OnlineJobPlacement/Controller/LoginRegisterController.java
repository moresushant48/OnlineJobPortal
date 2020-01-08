package com.example.OnlineJobPlacement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.OnlineJobPlacement.Model.User;
import com.example.OnlineJobPlacement.Model.Role;
import com.example.OnlineJobPlacement.Repository.UserRepository;

@Controller
public class LoginRegisterController {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/login")
	public ModelAndView loginGET() {
		return new ModelAndView("login");
	}
	
	@GetMapping("/register")
	public ModelAndView registerGET(Model model) {
		model.addAttribute("registerData", new User());
		return new ModelAndView("register");
	}
	
	@PostMapping("/register")
	public ModelAndView registerPOST(@ModelAttribute("registerData") User user, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("register");
		
		if(!bindingResult.hasErrors()) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			Role role = new Role();
			role.setId(3);
			user.setRole(role);
			if(userRepository.save(user) != null) {
				mv.addObject("result", "Registered Successfully.");
			} else mv.addObject("result", "Registration Unsuccessful.");
			
		}
		return mv;
	}
}
