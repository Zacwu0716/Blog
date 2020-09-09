package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.vo.Blog;
import com.example.demo.vo.BlogQuery;

public interface BlogService {
	
	Blog getBlog(Long id);
	
	Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
	
	Page<Blog> listBlog(Pageable pageable);
	
	Page<Blog> listBlog(String query,Pageable pageable) ;
	
	Page<Blog> listBlog(Pageable pageable,Long tagId);
	
	List<Blog> listcommentBlogTop(Integer size);
	
	
	Map<String,List<Blog>> archiveBlog();
	
	Long countBlog();
	
	Blog saveBlog(Blog blog);
	
	Blog updateBlog(Long id,Blog blog);
	
	void deleteBlog(Long id);
	
	Blog getAndConvert(Long id);
	
	
	
	

}
