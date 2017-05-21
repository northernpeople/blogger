package stepan.bloggger.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stepan.bloggger.user.Role;
import stepan.bloggger.user.User;
import stepan.bloggger.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService service;
	
	@RequestMapping(value = "/register_form", method = RequestMethod.GET)
	public String registerForm(Model m){
		m.addAttribute("registration_request", new User());
		return "user/register_form";	
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid User user, Errors errors, Model m){
		if(errors.hasErrors()) return "redirect:user/register_form";
		service.create(user, Role.ROLE_USER);
		return "redirect:/logout";
	}
	


}
