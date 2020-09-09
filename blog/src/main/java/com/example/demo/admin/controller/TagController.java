package com.example.demo.admin.controller;

import java.util.jar.Attributes;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.TagService;
import com.example.demo.vo.Tag;

@Controller
@RequestMapping("/admin")
public class TagController {

	@Autowired
	private TagService tagservice;
	
	@GetMapping("/tags")
	public String tags(@PageableDefault(size = 3, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		
		model.addAttribute("page",tagservice.listTag(pageable));
		System.out.println(tagservice.listTag(pageable));
				return "admin/tags";
	}
	
	@GetMapping("/tags/input")
	public String input(Model model) {
		model.addAttribute("tag",new Tag());
		
		return "admin/tags-input";
		
	}
	
	@GetMapping("/tags/{id}/input")
	public String editInput(@PathVariable Long id,Model model ) {
		System.out.println("11111");
		model.addAttribute("tag",tagservice.getTag(id));
		System.out.println(tagservice.getTag(id));
		return "admin/tags-input";
	}
	
	@PostMapping("/tags")
	public String post(@Valid Tag tag,BindingResult result,RedirectAttributes addAttributes) {
		 
		Tag tag1 = tagservice.getTagByName(tag.getName());
		
		if(tag1 !=null) {
			result.rejectValue("name", "nameError","分類已存在");
		}
		
		if (result.hasErrors()) {
			return "admin/tags-input";
		}
		
		Tag t = tagservice.saveTag(tag);
		
		if(t ==null) {
			addAttributes.addFlashAttribute("message","新增失敗");
		}else {
			addAttributes.addFlashAttribute("message","新增成功");
		}
		 return "redirect:/admin/tags";
	}
	
	@PostMapping("/tags/{id}")
	public String editPost(@Valid Tag tag,BindingResult result,RedirectAttributes addAttributes) {
		 
		Tag tag1 = tagservice.getTagByName(tag.getName());
		
		if(tag1 !=null) {
			result.rejectValue("name", "nameError","分類已存在");
		}
		
		if (result.hasErrors()) {
			return "admin/tags-input";
		}
		
		Tag t = tagservice.saveTag(tag);
		
		if(t ==null) {
			addAttributes.addFlashAttribute("message","新增失敗");
		}else {
			addAttributes.addFlashAttribute("message","新增成功");
		}
		 return "redirect:/admin/tags";
	}
	
	@GetMapping("/tags/{id}/delete")
	public String delete (@PathVariable Long id,RedirectAttributes addAttributes) {
		
		tagservice.deltetTag(id);
		addAttributes.addFlashAttribute("message","刪除成功");
		
		return "redirect:/admin/tags";
	}
	
}
