package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.BlogService;

@Controller
public class ArchiveShowController {
	
	
	@Autowired
	private BlogService blogservice;
	
	
	@GetMapping("/archives")
	public String archives(Model model) {
		model.addAttribute("archiveMap", blogservice.archiveBlog());
		model.addAttribute("blogcount",blogservice.countBlog());
		
		return "archives";
	}

}
