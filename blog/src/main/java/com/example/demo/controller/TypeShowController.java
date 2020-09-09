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
import com.example.demo.service.TypeService;
import com.example.demo.vo.BlogQuery;
import com.example.demo.vo.Type;

@Controller
public class TypeShowController {
	
	@Autowired
	private TypeService typeservice;
	
	@Autowired
	private BlogService blogservice;
	
	//跳轉到分類頁
	@GetMapping("/types/{id}")
	public String types(@PageableDefault(size = 2, sort = { "updateTime" }, direction = Sort.Direction.DESC) Pageable pageable,
			@PathVariable Long id,Model model) {
		System.out.println("123123213");
		//排行前幾筆
		List<Type> types = typeservice.ListTypeTop(10000);//查詢所有的分類數據
		
		//從導航點進來
		if(id==-1) {
			id = types.get(0).getId(); //取得第第一個分類id
		}
		BlogQuery blogquery = new BlogQuery();
		blogquery.setTypeId(id);
		System.out.println(types);
		model.addAttribute("types",types);
		model.addAttribute("page",blogservice.listBlog(pageable,blogquery));
		model.addAttribute("activeTypeId", id);
		return "types";
	}

}
