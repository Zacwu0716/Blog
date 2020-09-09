package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.service.BlogService;
import com.example.demo.service.CommentService;
import com.example.demo.vo.Comment;
import com.example.demo.vo.User;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentservice;

	@Autowired
	private BlogService blogservice;

	@Value("${comment.avatar}")
	private String avatar;

	// 取得評論列表
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model) {

		model.addAttribute("comments", commentservice.listCommentByBlogId(blogId));

		return "blog :: commentList";
	}

	// 取得評論列表
	@PostMapping("/comments/{blogId}")
	public String comment(@PathVariable Long blogId, Model model) {

		model.addAttribute("comments", commentservice.listCommentByBlogId(blogId));

		return "blog :: commentList";
	}

	// 發佈＆回復評論
	@PostMapping("/comments")
	public String post(Comment comment, HttpSession session) {
		Long blogId = comment.getBlog().getId();
		comment.setBlog(blogservice.getBlog(blogId));
		User user = (User) session.getAttribute("user");
		// 管理員帳號登入
		if (user != null) {
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);
//			comment.setNickname(user.getNickname());
		} else {
			comment.setAvatar(avatar);
		}

		commentservice.saveComment(comment); // 儲存評論
		return "redirect:/comments/" + blogId;
	}

}
