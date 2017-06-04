package stepan.bloggger.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		return "register_form";	
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid User user, Errors errors, RedirectAttributes m){
		if(errors.hasErrors()){
			m.addFlashAttribute("messages", Arrays.asList("please fix these errors"));
			return "redirect:/user/register_form";
		}
		userService.create(user, Role.ROLE_USER);
		m.addFlashAttribute("messages", Arrays.asList("You have been successfully registered"));
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model m, Principal p){
		m.addAttribute("user_id", userService.byUserName(p.getName()).getId());
		m.addAttribute("posts", postService.findAllByUsername(p.getName()));
		m.addAttribute("new_post", new Post());
		return "user/main";	
	}
	
	@RequestMapping(value="/create_post", method= RequestMethod.POST)
	public String createPost(@Valid Post post, 
							Errors errors,
							@RequestParam("file") MultipartFile file, 
							RedirectAttributes model,
							Principal p ) throws IOException{
		
		if(errors.hasErrors() || file.isEmpty()){
			model.addAttribute("messages", Arrays.asList("Please fix the errors"));
			return "redirect:/user/main";
		}
		User currentUser = userService.byUserName(p.getName());
		post.setOwner(currentUser);
		post = postService.create(post);
		imageService.create(file, post.getId());
		model.addFlashAttribute("messages", Arrays.asList("post created"));
		return "redirect:/user/main";	
	}
	
	@RequestMapping("edit_post/{id}")
	public String editForm(@PathVariable("id") Long id, Model m){
		m.addAttribute(postService.findById(id));
		return "user/edit_post";
	}
	
	@RequestMapping("delete_post/{id}")
	public String deletePost(@PathVariable("id") Long id, RedirectAttributes model) throws IOException{
		imageService.delete(imageService.findByPostId(id));
		postService.delete(id);
		model.addFlashAttribute("messages", Arrays.asList("post deleted") );
		return "redirect:/user/main";	
	}
	
	@RequestMapping(value ="edit_post", method= RequestMethod.POST )
	public String edit(@Valid Post post,
						Errors errors,
						RedirectAttributes model,
						Principal p ) throws IOException{

			if(errors.hasErrors()){
				model.addFlashAttribute("messages", Arrays.asList("Please fix errors"));
				return "redirect:/user/edit_post/"+post.getId();	
			}
			post = postService.update(post);
			model.addFlashAttribute("messages", Arrays.asList("Successfully edited post"));
			return "redirect:/user/main";			
	}
				
				
				


}
