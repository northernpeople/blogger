	package stepan.bloggger.web.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import stepan.bloggger.image.Image;
import stepan.bloggger.image.ImageService;

@Controller
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	ImageService service;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> rawEvidence(@PathVariable Long id){
		Image image = service.getById(id);
		Resource file = service.get(image);
		try {
			return ResponseEntity.ok()
					.contentType(MediaType.valueOf(image.getContentType()))
					.contentLength(file.contentLength())
					.body(new InputStreamResource(file.getInputStream()));
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("image not found " + e.getMessage());
		}
	}
	
	@RequestMapping(value="/form/{id}")
	public String evidenceForm(	@PathVariable("id") Long pid, Model m){
		m.addAttribute("post_id", pid);
		return "evidence_form"; 
	}
	
	@RequestMapping( value="/save", method = RequestMethod.POST)
	public String createEvidence(	@RequestParam("file") MultipartFile file,
									@RequestParam("post_id") Long p_id,
									Model model	) throws IOException{
		
		if(file.isEmpty()){
			model.addAttribute("messages", Arrays.asList("File is required"));
			model.addAttribute("post_id", p_id);
			return "evidence_form"; 
		}
		
		if(! file.getContentType().equals("image/jpeg")){
			model.addAttribute("messages", Arrays.asList("Only files with .jpeg extension are allowed"));
			model.addAttribute("post_id", p_id);
			return "evidence_form"; 
		}
		service.delete(service.findByPostId(p_id));
		service.create(file, p_id);
		model.addAttribute("messages", Arrays.asList("Image saved"));
		return "redirect:/route";
	}
	
}
