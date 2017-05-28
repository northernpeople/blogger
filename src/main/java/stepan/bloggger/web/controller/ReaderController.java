package stepan.bloggger.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import stepan.bloggger.image.Image;
import stepan.bloggger.image.ImageService;
import stepan.bloggger.post.Post;
import stepan.bloggger.post.PostService;

@Controller
@RequestMapping("/read")
public class ReaderController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	ImageService imageService;
	
	@RequestMapping("/overview/{id}")
	public String postListOf(@PathVariable("id") Long id, Model m){
		m.addAttribute("posts", postService.findAllByUserId(id));
		return "read/overview";
	}
	
	@RequestMapping("/post/{id}")
	public String preview(@PathVariable("id") Long id, Model m){
		Post post = postService.findById(id);
		m.addAttribute(post);
		Image image= imageService.findByPostId(post.getId());
		if(image != null) m.addAttribute(image);
		return "read/post";
	}

}
