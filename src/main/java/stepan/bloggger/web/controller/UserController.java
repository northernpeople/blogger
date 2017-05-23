package stepan.bloggger.web.controller;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import stepan.bloggger.image.Image;
import stepan.bloggger.image.ImageService;
import stepan.bloggger.post.Post;
import stepan.bloggger.post.PostService;
import stepan.bloggger.user.Role;
import stepan.bloggger.user.User;
import stepan.bloggger.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	ImageService imageService;
	
	@RequestMapping(value = "/register_form", method = RequestMethod.GET)
	public String registerForm(Model m){
		m.addAttribute("registration_request", new User());
		return "user/register_form";	
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid User user, Errors errors, Model m){
		if(errors.hasErrors()) return "redirect:user/register_form";
		userService.create(user, Role.ROLE_USER);
		return "redirect:/logout";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model m, Principal p){
		m.addAttribute("posts", postService.findAllByUsername(p.getName()));
		m.addAttribute("new_post", new Post());
		return "user/main";	
	}
	
	@RequestMapping(value="/create_post", method= RequestMethod.POST)
	public String createPost(@Valid Post post, 
							@RequestParam("file") MultipartFile file, 
							Errors errors, 
							Principal p ) throws IOException{
		
		if(errors.hasErrors()) return "redirect:/user/main";
		
		User currentUser = userService.byUserName(p.getName());
		post.setOwner(currentUser);
		post = postService.create(post);
		System.out.println(	imageService.create(file, post.getId()) );
		return "redirect:/user/main";
		
	}
	
	@RequestMapping("preview_post/{id}")
	public String preview(@PathVariable("id") Long id, Model m){
		Post post = postService.findById(id);
		Image image= imageService.findByPostId(post.getId());
		m.addAttribute(post);
		m.addAttribute(image);
		return "user/post_preview";
	}
	
	@RequestMapping("edit_post/{id}")
	public String edit_form(@PathVariable("id") Long id, Model m){
		m.addAttribute(postService.findById(id));
		return "user/edit_post";
		
	}
	
	@RequestMapping(value ="edit_post", method= RequestMethod.POST )
	public String edit(@Valid Post post,
						Errors errors, 
						Principal p ) throws IOException{

			if(errors.hasErrors()) return "redirect:/user/main";	
			post = postService.update(post);
			return "redirect:/user/main";
					
	}
				
				
				


}
