package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.helper.Message;
@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepostory;
	
	@GetMapping("/")
	public String getHome(Model mode) {
	mode.addAttribute("title", "Home-Smart Contact Manger");
		return "home";
	}
	@GetMapping("/about")
	public String about(Model mode) {
		mode.addAttribute("title", "About-Smart Contact Manager");
		return "about";
		
		
	}
	@GetMapping("/signup")
	public String signup(Model mode) {
		mode.addAttribute("title", "Register-Smart Contact Manager");
		mode.addAttribute("user", new User());
		return "signup";
		
	}
	
	@GetMapping("/login")
	public String login(Model mode) {

		mode.addAttribute("title", "LoginPage-Smart Contact Manager");
		
		return "login";
		
	}
		
	
	
	//handler for register user  /do_register
	@RequestMapping( value="/do_register" , method=RequestMethod.POST)
	public String doRegister( @Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value="agreement" ,defaultValue="false") boolean agreement, Model model, HttpSession session) {
	try {
		
		if(!agreement) {
			System.out.println("You  have not agreed on terms andd conditions");
			throw new Exception("you have not agered terms and conditions");
		}
		if(result1.hasErrors()) {
			System.out.println("ERROR" +result1.toString());
			model.addAttribute("user", user);
			return "signup";
		}
	user.setRole("Role_user");
	user.setEnabled(true);
	user.setImageURL("default.png");
	
	
		System.out.println("Agreement" +agreement);
		System.out.println("User" +user);
	 User result =	this.userRepostory.save(user);
		model.addAttribute("user", new User());
		session.setAttribute("message", new Message("SuccessFully Registereed!!", "alert-sucess"));
		return "signup";
	} catch (Exception e) {
		e.printStackTrace();
		model.addAttribute("user", user);
		session.setAttribute("message", new Message("Something went wrong!! " +e.getMessage(),"alert-danger"));
		return "signup";
		
	}
	
	}
	

}
