package com.example.demo.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.BlogService;
import com.example.demo.service.TagService;
import com.example.demo.service.TypeService;
import com.example.demo.vo.Blog;
import com.example.demo.vo.BlogQuery;
import com.example.demo.vo.Type;
import com.example.demo.vo.User;

@Controller
@RequestMapping("/admin")
public class BlogController {

	
	private static final String INPUT = "/admin/blogs-input";
	private static final String LIST = "/admin/blogs";
	private static final String REDIRECT_LIST = "redirect:/admin/blogs";
	
	@Autowired
	private BlogService blogservice;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagservice;
	
	/*@GetMapping("/blogs")
	public String blogs() {
		
		return "admin/blogs";
		
	}*/
		
	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size = 2, sort = { "updateTime" }, direction = Sort.Direction.DESC) Pageable pageable,BlogQuery blog,Model model) {
		
		model.addAttribute("types",typeService.ListType());
		model.addAttribute("page", blogservice.listBlog(pageable, blog));
		//model.addAttribute("page", typeService.listType(pageable));
		return LIST;
	}
	
	//查詢方法 ajax
	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size = 2, sort = { "updateTime" }, direction = Sort.Direction.DESC) Pageable pageable,BlogQuery blog,Model model) {
		
		model.addAttribute("page", blogservice.listBlog(pageable, blog));
		return "admin/blogs :: blogList";
	}
	
	//新增Blog
	@GetMapping("/blogs/input")
	public String input(Model model) {
		//分類初始化		
		setTypeAndTag(model);
		
		model.addAttribute("blog", new Blog());
		return INPUT;
	}
	
	private void setTypeAndTag(Model model) {
		
		model.addAttribute("types",typeService.ListType());
		model.addAttribute("tags", tagservice.listTag());
		
	}
	
	//修改Blog
		@GetMapping("/blogs/{id}/input")
		public String editinput(@PathVariable Long id ,Model model) {
			setTypeAndTag(model);//分類初始化
			
			Blog blog =  blogservice.getBlog(id);
			blog.init();
			System.out.println(blog);
			model.addAttribute("blog",blog);
		
			return INPUT;
		}
		
	//儲存ㄉ資料
	@PostMapping("/blogs")
	public String post(Blog blog,RedirectAttributes addrAttributes, HttpSession session) {
		blog.setUser((User)session.getAttribute("user"));
		blog.setType(typeService.getType(blog.getType().getId()));
		blog.setTags(tagservice.listTag(blog.getTagIds()));
		
		Blog b = blogservice.saveBlog(blog);
		
		if(blog.getId()== 0) {
			b = blogservice.saveBlog(blog);
		}else {
			b = blogservice.updateBlog(blog.getId(), blog);
		}
		
		if (b == null) {
			addrAttributes.addFlashAttribute("message", "操作失敗");
		} else {
			addrAttributes.addFlashAttribute("message", "操作成功");
		}
		return REDIRECT_LIST;
	}
	
	//刪除
	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes addrAttributes) {
		blogservice.deleteBlog(id);
		addrAttributes.addFlashAttribute("message", "刪除成功");
		return REDIRECT_LIST;
		
	}
	
	
}
