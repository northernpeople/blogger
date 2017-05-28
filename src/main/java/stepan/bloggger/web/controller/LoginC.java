package stepan.bloggger.web.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import stepan.bloggger.service.email.EmailService;
import stepan.bloggger.user.User;
import stepan.bloggger.user.UserService;
import stepan.bloggger.web.command.PasswordChange;


@Controller
public class LoginC{
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@RequestMapping(value = "/route", method = RequestMethod.GET)
	public String createUser(Principal principal) {
		User current = userService.byUserName(principal.getName());
		switch(current.getRole()){
			case ROLE_ADMIN:
				return "redirect:/admin/main";
			case ROLE_USER:
				return "redirect:/user/main";
			default:
				return "redirect:/login";	
		}	
	}
	
	@RequestMapping(path = {"/login", "/"}, method = RequestMethod.GET)
	public String userForm(Model model) {
		model.addAttribute("user", new User());
		return "user_form";
	}
	
	@RequestMapping(value="/change_password_form", method = RequestMethod.GET)
	public String changePasswordForm(Model model){
		model.addAttribute("request", new PasswordChange());
		return "change_password_form";
	}
	
	@RequestMapping(value="/change_password", method=RequestMethod.POST)
	public String changePassword(@Valid PasswordChange request, Errors errors, RedirectAttributes model){
		if(errors.hasErrors()){
			model.addFlashAttribute("warning", "something is not right");
			return "change_password_form";		
		}
		User currentUser = userService.currentUser();
		if(currentUser == null || ! encoder.matches(request.getOld(), currentUser.getPassword())){
			model.addFlashAttribute("warning", "please sign in again");
			return "redirect:/";
		}
		if(! request.getNew1().equals(request.getNew2())){
			errors.rejectValue("new2", "Match", "new passwords must match");
			return "change_password_form";		
		}
		currentUser.setPassword(request.getNew1());
		userService.rehashPassword(currentUser);
		emailService.send(currentUser.getUsername(), 
				"Your password has been changed!", 
				"Please contact your administrator if you did not change it");
		return "redirect:/logout";
	}

	
}
