package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Comment;
import com.example.demo.vo.Tag;

public interface CommentRepository extends JpaRepository<Comment,Long>{
	
	
	List<Comment> findByBlogIdAndParentCommentNull(Long blogId,Sort sort);

}
