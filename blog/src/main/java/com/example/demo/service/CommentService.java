package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.Comment;

public interface CommentService {
	
	List<Comment> listCommentByBlogId(Long BolgId);

	Comment saveComment(Comment comment);
}
