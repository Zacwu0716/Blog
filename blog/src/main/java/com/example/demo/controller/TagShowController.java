package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.service.BlogService;
import com.example.demo.service.TagService;
import com.example.demo.vo.Tag;

@Controller
public class TagShowController {
	
	@Autowired
	private TagService tageservice;
	
	@Autowired
	private BlogService blogservice;
	
	//跳轉到分類頁
	@GetMapping("/tags/{id}")
	public String tags(@PageableDefault(size = 2, sort = { "updateTime" }, direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long id,Model model) {
		
		
		//排行前幾筆
		List<Tag> tags = tageservice.listTagTop(10000);//查詢所有的分類數據
		
		//從導航點進來
		if(id==-1) {
			id = tags.get(0).getId(); //取得第第一個分類id
		}
	
		model.addAttribute("tags",tags);
		model.addAttribute("page",blogservice.listBlog(pageable,id));
		model.addAttribute("activeTagId", id);
		return "tags";
	}

}
