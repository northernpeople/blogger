	package stepan.bloggger.web.controller;

import java.io.IOException;

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


import stepan.bloggger.image.ImageService;

@Controller
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	ImageService service;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/{fileName:.+}")
	public ResponseEntity<?> rawEvidence(@PathVariable String fileName){
		Resource file = service.getBy(fileName);
		try {
			return ResponseEntity.ok()
					.contentType(MediaType.valueOf(service.typeByFileName(fileName)))
					.contentLength(file.contentLength())
					.body(new InputStreamResource(file.getInputStream()));
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("image not found " + e.getMessage());
		}
	}
	
	@RequestMapping(value="/form")
	public String evidenceForm(){ return "evidence_form"; }
	
	@RequestMapping(method = RequestMethod.POST, value="/save")
	public String createEvidence(@RequestParam("file") MultipartFile file){
		try { service.create(file); } 
		catch (IOException e) { e.printStackTrace(); }
		return "redirect:/";
	}
	
	
	@RequestMapping(value = "/delete/{fileName}", method = RequestMethod.GET)
    public String delete (@PathVariable("fileName") String fileName, Model model) {
		try { service.delete(fileName); } 
		catch (IOException e) { e.printStackTrace(); }
		return "redirect:/";

	}
	
	@RequestMapping(value="/list")
	public String list(Model m){
		m.addAttribute("evidence", service.getAll());
		return "admin/evidence_list";
	}
}
