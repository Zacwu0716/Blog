package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.NotFoundException;
import com.example.demo.service.BlogService;
import com.example.demo.service.TagService;
import com.example.demo.service.TypeService;
import com.example.demo.vo.BlogQuery;

@Controller
public class IndexController {

	@Autowired
	private BlogService blogservice;

	@Autowired
	private TypeService typeservice;

	@Autowired
	private TagService tagservice;

	@GetMapping("/")
	public String index(
			@PageableDefault(size = 2, sort = { "updateTime" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {

		model.addAttribute("page", blogservice.listBlog(pageable));
		model.addAttribute("types", typeservice.ListTypeTop(6));
		model.addAttribute("tags", tagservice.listTagTop(6));
		model.addAttribute("recommandBlogs", blogservice.listcommentBlogTop(8));
		return "index";

	}

	//全局查詢
	@PostMapping("/search")
	public String search(
			@PageableDefault(size = 2, sort = { "updateTime" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model, @RequestParam String query) {

		model.addAttribute("page", blogservice.listBlog("%" + query + "%", pageable));
		model.addAttribute("query", query);
		return "search";

	}

	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id,Model model) {
		
		model.addAttribute("blog",blogservice.getBlog(id));
		//model.addAttribute("blog",blogservice.getBlog(id));
		
		return "blog";

	}
	@GetMapping("/footer/newblog")
	public String newblogs(Model model) {
		model.addAttribute("newblogs", blogservice.listcommentBlogTop(3));
		
		return"_fragment:: newblogList";
	}

}
